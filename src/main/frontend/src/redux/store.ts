import { configureStore } from '@reduxjs/toolkit';
import { accountSlice } from './reducer';

export const store = configureStore({
    reducer: {
        account_state: accountSlice.reducer
    }
});