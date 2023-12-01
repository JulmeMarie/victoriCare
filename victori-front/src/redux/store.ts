import { configureStore } from '@reduxjs/toolkit';
import { appSlice } from './reducers/app-reducer';
import { userSlice } from './reducers/user-reducer';

export const store = configureStore({
    reducer: {
        common_state: appSlice.reducer,
        user_state: userSlice.reducer
    }
});

export type AppState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;