import { createSlice } from '@reduxjs/toolkit';
import { appActions } from './actions/app-actions';

export const appSlice = createSlice({
    name: "app_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
        token: null,
        user: {
            userName: null,
            sexe: null,
            role: null,
            logInAt: null,
            profil: {
                firstName: null,
                lastName: null,
                photo: null,
                email: null,
                signInAt: null,
                lang: "fr",
            },
        },
    },
    reducers: appActions
});

export const { logIn, logOut, updatePassword, refreshToken, signIn, updateUser, updateLang, initLang, deleteUser } = appSlice.actions;

