import { EOrderType } from "./global-types"

export interface IResult {
    isLoading: boolean,
    isLoaded?: boolean,
    isError?: boolean,
    data?: any,
    success?: string,
    error?: string
}

export interface KeyPair {
    key: string,
    value: string
}

export interface IUserAction {
    name: string,
    result: IResult
}

export interface User {
    firstname: string,
    lastname: string
}
export interface LogIn {
    user: User,
    token: string,
    createAt: string,
    expireAt: string | null
}

export interface ISelectOption {
    value: string,
    label: string
}

export interface ICare {
    createAt: string,
    endAt?: string,
    createFor: string,
    createBy: string,
    during: number,
    title: string,
    description?: string,
    status: string,
    isSelected?: boolean,
    moreDetails?: {}
}