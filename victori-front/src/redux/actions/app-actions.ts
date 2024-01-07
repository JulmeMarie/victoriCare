import { PayloadAction } from "@reduxjs/toolkit";
import { LANGUAGES } from "../../utils/Constants";
import { IResult, IUserAction, LogIn, User } from "../../utils/global-interfaces";
import { ELanguageType } from "../../utils/global-types";
import { IAppState } from "../reducers/app-reducer";

export const appActions = {
    setLoadingDoc: (state: IAppState) => {
        const result: IResult = { isLoading: true };
        state.documentation = result;
        return state;
    },

    setDrawerStatus: (state: IAppState, action: PayloadAction<boolean>) => {
        state.sideNaveStatus = action.payload;
        return state;
    },

    setUserAction: (state: IAppState, action: PayloadAction<IUserAction>) => {
        state.userAction = action.payload;
        return state;
    },

    setLogIn: (state: IAppState, action: PayloadAction<LogIn>) => {
        state.logIn = action.payload;
        return state;
    },

    setLoadedDoc: (state: IAppState, action: PayloadAction<IResult>) => {
        state.documentation = action.payload;
        return state;
    },

    setLoadingComments: (state: IAppState) => {
        const result: IResult = { isLoading: true };
        state.comments = result;
        return state;
    },

    setLoadedComments: (state: IAppState, action: PayloadAction<IResult>) => {
        state.comments = action.payload;
        return state;
    },

    initLang: (state: IAppState) => {
        let lang = localStorage.getItem("babycare-lang");
        if (!lang) {
            lang = navigator.language.split("-")[0];
        }
        state.lang = lang ? lang as ELanguageType : LANGUAGES.FR;
        return state;
    },
    setScroll: (state: IAppState, action: PayloadAction<number>) => {
        const scroll = action.payload < 0 ? 0 : action.payload;
        state.scroll = scroll;
    },
    updateLang: (state: IAppState, action: PayloadAction<ELanguageType>) => {
        state.lang = action.payload;
        localStorage.setItem("babycare-lang", action.payload);
        return state;
    },
    refreshToken: (state: IAppState, action: PayloadAction) => {
        return state;
    },
}