import { createSlice } from '@reduxjs/toolkit';
import { accountActions } from './actions/account-actions';

export const accountSlice = createSlice({
    name: "account_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
        token: null,
        user: null,
        createAt: null,
    },
    reducers: accountActions
});

export const { logIn, logOut, updatePassword, refreshToken, signin, checkAccount, updateAccount, deleteAccount } = accountSlice.actions;

