import { configureStore } from '@reduxjs/toolkit';
import { appSlice } from './reducer';

export const store = configureStore({
    reducer: {
        app_state: appSlice.reducer
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;