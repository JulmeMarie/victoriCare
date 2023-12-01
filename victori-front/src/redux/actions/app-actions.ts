import { PayloadAction } from "@reduxjs/toolkit";
import { IResult, IUserAction, User } from "../../utils/global-interfaces";
import { IAppState } from "../reducers/app-reducer";

export const appActions = {
    setLoadingDoc: (state: IAppState) => {
        const result: IResult = { isLoading: true };
        state.documentation = result;
        return state;
    },

    setSideNavStatus: (state: IAppState, action: PayloadAction<boolean>) => {
        state.sideNaveStatus = action.payload;
        return state;
    },

    setUserAction: (state: IAppState, action: PayloadAction<IUserAction>) => {
        state.userAction = action.payload;
        return state;
    },

    setOnlineUser: (state: IAppState, action: PayloadAction<User>) => {
        state.onlineUser = action.payload;
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

    initDevice: (state: IAppState) => {
        // eslint-disable-next-line no-restricted-globals
        const width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
        state.device = width;
        return state;
    },

    initLang: (state: IAppState) => {
        let lang = localStorage.getItem("babycare-lang");
        if (!lang) {
            lang = navigator.language.split("-")[0];
        }
        state.lang = lang ? lang : "fr";
        return state;
    },
    setScroll: (state: IAppState, action: PayloadAction<number>) => {
        const scroll = action.payload < 0 ? 0 : action.payload;
        state.scroll = scroll;
    },
    updateLang: (state: IAppState, action: PayloadAction<string>) => {
        state.lang = action.payload;
        localStorage.setItem("babycare-lang", action.payload);
        return state;
    },
    refreshToken: (state: IAppState, action: PayloadAction) => {
        return state;
    },
}