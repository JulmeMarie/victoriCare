export interface IResult {
    isLoading: boolean,
    isLoaded?: boolean,
    isError?: boolean,
    data?: any,
    success?: string,
    error?: string
}

export interface AlertType {
    key: string,
    value: string
}

export interface IUserAction {
    name: string,
    result: IResult
}

export interface User {

}

export interface LogIn {
    user: User,
    token: string,
    createAt: Date,
    expireAt: Date | null
}

export interface ISelectOption {
    value: string,
    label: string
}