import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import { enableMapSet } from 'immer';
import { appSlice } from './reducers/app-reducer';
import { dataTableSlice } from './reducers/data-table-reducer';
import { userSlice } from './reducers/user-reducer';
import { notificationSlice } from './reducers/notification-reducer';

enableMapSet();
export const store = configureStore({
    reducer: {
        commonReducer: appSlice.reducer,
        userReducer: userSlice.reducer,
        datatableReducer: dataTableSlice.reducer,
        notificationReducer: notificationSlice.reducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware({ serializableCheck: false }),
    devTools: { serialize: { options: true } },
});

export type AppState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;