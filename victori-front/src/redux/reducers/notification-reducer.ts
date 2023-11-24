import { createSlice } from '@reduxjs/toolkit';
import { notificationActions } from '../actions/notification-actions';

export interface INotification {
    id: number,
    title: string;
    description: string;
    author?: string,
    createAt: number,
    seenAt?: number,
}
export const notificationSlice = createSlice({
    name: "notificationReducer",
    initialState: {
        all: [] as Array<INotification>,
        seen: [] as Array<number>,
        unSeen: [{
            id: 1,
            title: "Soins créé",
            description: "Votre enfant a pris son bain à 13h",
            author: "JULME",
            createAt: 23121454545,
        }, {
            id: 2,
            title: "Evénement créé",
            description: "Un évément veient d'être créé",
            author: "Samano",
            createAt: 231215454545,
        }] as Array<INotification>,
    },
    reducers: notificationActions
});

export const { isSeen, setNew, setAll } = notificationSlice.actions;