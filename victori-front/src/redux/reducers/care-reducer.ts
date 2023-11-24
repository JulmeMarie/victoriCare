import { createSlice } from '@reduxjs/toolkit';
import { careActions } from '../actions/care-actions';

export const careSlice = createSlice({
    name: "care_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
    },
    reducers: careActions
});

export const { } = careSlice.actions;