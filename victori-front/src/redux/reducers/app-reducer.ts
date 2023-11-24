import { createSlice } from '@reduxjs/toolkit';
import { IResult, IUserAction, LogIn, User } from '../../utils/global-interfaces';
import { appActions } from '../actions/app-actions';
import { EDeviceType, ELanguageType } from '../../utils/global-types';
import { DEFAULT_LANG } from '../../utils/languages/I18N';
import { mockLogin } from '../../utils/global-default-values';

export interface IAppState {
    documentation: IResult;
    comments: IResult;
    token: string | null;
    logIn: LogIn | null;
    userAction: IUserAction;
    scroll: number;
    lang: ELanguageType;
    sideNaveStatus: boolean;
}

export const appSlice = createSlice({
    name: "commonReducer",
    initialState: {
        documentation: {},
        comments: {},
        token: null,
        logIn: null,
        userAction: {},
        scroll: 0,
        lang: DEFAULT_LANG,
        sideNaveStatus: false
    } as IAppState,
    reducers: appActions
});

export const { setDrawerStatus, setLogIn, setUserAction, setLoadingDoc, setLoadedDoc, setLoadingComments, setLoadedComments, refreshToken, updateLang, initLang, setScroll } = appSlice.actions;