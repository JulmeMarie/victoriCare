import { createSlice } from '@reduxjs/toolkit';
import { nameActions } from '../actions/name-actions';

export const nameSlice = createSlice({
    name: "name_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
    },
    reducers: nameActions
});

export const { } = nameSlice.actions;