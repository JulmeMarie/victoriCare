import { createSlice } from '@reduxjs/toolkit';
import { IResult, IUserAction, User } from '../../utils/global-interfaces';
import { DEVICES } from '../../utils/Constants';
import { appActions } from '../actions/app-actions';

export interface IAppState {
    documentation: IResult;
    comments: IResult;
    token: string | null;
    onlineUser: User | null;
    userAction: IUserAction;
    scroll: number;
    lang: string;
    device: number;
    sideNaveStatus: boolean;
}

export const appSlice = createSlice({
    name: "common_state",
    initialState: {
        documentation: {},
        comments: {},
        token: null,
        onlineUser: null,
        userAction: {},
        scroll: 0,
        lang: "fr",
        device: DEVICES.DEFAULT,
        sideNaveStatus: false
    } as IAppState,
    reducers: appActions
});

export const { setSideNavStatus, initDevice, setOnlineUser, setUserAction, setLoadingDoc, setLoadedDoc, setLoadingComments, setLoadedComments, refreshToken, updateLang, initLang, setScroll } = appSlice.actions;