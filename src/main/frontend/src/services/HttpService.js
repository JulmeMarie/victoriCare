import { ConnectionService } from "./LoginService";

const baseUrl = 'http://ec2-35-180-67-64.eu-west-3.compute.amazonaws.com:5000/api/';
//const baseUrl = 'http://ec2-35-180-83-119.eu-west-3.compute.amazonaws.com:8080/fmc-api/api/';
const apiKey = '6a080215750cf77ad037ec16eb862c808cfbff6ac55c62fb8b5aaaaf03fa96ee';
const successMessage = "Opération réussie.";
const errorMessage = "Une erreur s'est produite";

export const HttpService = {
    headers: (isJson = false) => {
        let token = ConnectionService.getTokenFromLocalStorage();

        if (token && isJson) {
            return {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token,
                "Fmc-Apikey": apiKey,
            }
        }
        else if (token) {
            return {
                "Authorization": "Bearer " + token,
                "Fmc-Apikey": apiKey,
            }
        }
        else {
            return {
                "Content-Type": "application/json",
                "Fmc-Apikey": apiKey,
            }
        }
    },

    responseData: async (response, withData = true) => {
        return {
            ok: response.ok,
            message: response.ok ? successMessage : errorMessage,
            data: response.ok && withData ? await response.json() : null
        }
    },

    create: async (body, endpoint, contentType, withResponse) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "PUT",
                headers: HttpService.headers(contentType === "JSON"),
                body: body
            });
            return HttpService.responseData(response, withResponse);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({}, withResponse);
        }

    },

    update: async (body, endpoint, contentType, withResponse) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "POST",
                headers: HttpService.headers(contentType === "JSON"),
                body: body
            });

            return HttpService.responseData(response, withResponse);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({}, withResponse);
        }
    },

    delete: async (endpoint, withResponse) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "DELETE",
                headers: HttpService.headers()
            });
            return HttpService.responseData(response, withResponse);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({}, withResponse);
        }
    },

    read: async (endpoint) => {
        try {
            let response = await fetch(baseUrl + endpoint, {
                method: "GET",
                headers: HttpService.headers()
            });
            return HttpService.responseData(response);
        }
        catch (err) {
            console.error(err);
            return HttpService.responseData({});
        }
    },
}

export default HttpService;