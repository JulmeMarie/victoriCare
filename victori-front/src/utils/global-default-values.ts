import { IResult } from "./global-interfaces"

export const defaultResult = {
    isLoading: false,
    isLoaded: false,
    isError: false,
    data: undefined,
    error: undefined
} as IResult


export const errorResult = {
    isLoading: false,
    isLoaded: false,
    isError: true,
    data: undefined,
    error: "une erreur est survenue"
} as IResult

export const successResult = {
    isLoading: false,
    isLoaded: false,
    isError: false,
    data: { data: "On a trouvé des données" },
    success: "succes",
    error: undefined
} as IResult