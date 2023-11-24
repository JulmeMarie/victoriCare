import { createSlice } from '@reduxjs/toolkit';
import { eventActions } from '../actions/event-actions';

export const eventSlice = createSlice({
    name: "event_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
    },
    reducers: eventActions
});

export const { } = eventSlice.actions;