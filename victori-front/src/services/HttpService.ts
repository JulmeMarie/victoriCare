import { IResult } from "../utils/global-interfaces";
import { CONTENTTYPES } from "../utils/Constants";

const baseUrl = 'http://ec2-35-180-67-64.eu-west-3.compute.amazonaws.com:5000/api/';
//const baseUrl = 'http://ec2-35-180-83-119.eu-west-3.compute.amazonaws.com:8080/fmc-api/api/';
const apiKey = '6a080215750cf77ad037ec16eb862c808cfbff6ac55c62fb8b5aaaaf03fa96ee';
const successMessage = "Opération réussie.";
const errorMessage = "Une erreur s'est produite";
const DELAY = 58 * 60 * 1000; //19mn

interface IvictoriToken {
    token: string,
    createAt: number
}

export const HttpService = {
    headers: (contentType?: string): HeadersInit => {
        contentType = contentType ? contentType : CONTENTTYPES.JSON;
        let token = HttpService.getTokenFromLocalStorage();
        if (token) {
            return {
                "Content-Type": contentType,
                "Authorization": "Bearer " + token,
                "Fmc-Apikey": apiKey,
            }
        }
        else {
            return {
                "Content-Type": contentType,
                "Fmc-Apikey": apiKey,
            }
        }
    },

    responseData: async (response: Response, withData = true) => {
        return {
            ok: response.ok,
            message: response.ok ? successMessage : errorMessage,
            data: response.ok && withData ? await response.json() : null
        }
    },

    create: async (body: BodyInit, endpoint: string, contentType: string, withResponse: boolean) => {
        const result = {} as IResult;
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "PUT",
                headers: HttpService.headers(contentType),
                body: body
            });
            const data = await response.json();
            result.data = data;
        }
        catch (err: any) {
            result.isError = true;
            result.error = err.message;
        }
        finally {
            result.isLoading = false;
            result.isLoaded = true;
            return result;
        }
    },

    update: async (body: BodyInit, endpoint: string, contentType: string, withResponse: boolean) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "POST",
                headers: HttpService.headers(contentType),
                body: body
            });

            return HttpService.responseData(response, withResponse);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({} as Response, withResponse);
        }
    },

    delete: async (endpoint: string, withResponse: boolean) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "DELETE",
                headers: HttpService.headers()
            });
            return HttpService.responseData(response, withResponse);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({} as Response, withResponse);
        }
    },

    read: async (endpoint: string) => {
        const result = {} as IResult;
        try {
            const response = await fetch(baseUrl + endpoint, {
                method: "GET",
                headers: HttpService.headers()
            });
            const data = await response.json();
            result.data = data;
        }
        catch (err: any) {
            result.isError = true;
            result.error = err.message;
        }
        finally {
            result.isLoading = false;
            result.isLoaded = true;
            return result;
        }
    },
    getTokenFromLocalStorage: (): IvictoriToken | null => {
        let victoriToken: IvictoriToken | null = null;
        try {
            let token = localStorage.getItem("victori-token");
            if (token) {
                victoriToken = JSON.parse(token) as IvictoriToken;
                if (victoriToken.createAt + DELAY <= new Date().getTime()) {
                    localStorage.removeItem("victori-token");
                }
            }
        } catch (e: any) {
            console.error(e.message);
        } finally {
            return victoriToken;
        }
    },

    setTokenToLocalStorage: (token: string) => {
        localStorage.setItem(
            "victori-token",
            JSON.stringify({
                token: token,
                createAt: new Date().getTime(),
            })
        );
    },
}

export default HttpService;