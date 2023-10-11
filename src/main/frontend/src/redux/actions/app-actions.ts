import { PayloadAction } from "@reduxjs/toolkit";

export const appActions = {
    logIn: (state: any, action: PayloadAction) => {
        return state;
    },
    logOut: (state: any, action: PayloadAction) => {
        return state;
    },
    updatePassword: (state: any, action: PayloadAction) => {
        //Here we should use fetch API
        return state;
    },
    initLang: (state: any) => {
        let lang = localStorage.getItem("babycare-lang");
        if (!lang) {
            lang = navigator.language.split("-")[0];
        }
        state.user.profil.lang = lang ? lang : "fr";
        return state;
    },

    updateLang: (state: any, action: PayloadAction<string>) => {
        state.user.profil.lang = action.payload;
        return state;
    },
    refreshToken: (state: any, action: PayloadAction) => {
        return state;
    },
    signIn: (state: any, action: PayloadAction) => {
        return state;
    },
    updateUser: (state: any, action: PayloadAction) => {
        return state;
    },
    deleteUser: (state: any, action: PayloadAction) => {
        return state;
    },
}