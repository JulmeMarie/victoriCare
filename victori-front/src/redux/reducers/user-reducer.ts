import { createSlice } from '@reduxjs/toolkit';
import { userActions } from '../actions/user-actions';

export const userSlice = createSlice({
    name: "userReducer",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
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
    reducers: userActions
});

export const { logIn, logOut, updatePassword, signIn, updateUser, deleteUser } = userSlice.actions;