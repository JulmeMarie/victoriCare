import { createSlice } from '@reduxjs/toolkit';
import { tossActions } from '../actions/toss-actions';

export const tossSlice = createSlice({
    name: "toss_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
    },
    reducers: tossActions
});

export const { } = tossSlice.actions;