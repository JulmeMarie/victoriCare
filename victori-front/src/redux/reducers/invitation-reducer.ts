import { createSlice } from '@reduxjs/toolkit';
import { invitationActions } from '../actions/invitation-actions';

export const invitationSlice = createSlice({
    name: "invitation_state",
    initialState: {
        status: {
            isLoading: false,
            isLoaded: false,
            error: null,
        },
    },
    reducers: invitationActions
});

export const { } = invitationSlice.actions;