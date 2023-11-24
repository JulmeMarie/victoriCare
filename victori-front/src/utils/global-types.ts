import { ACTIONS, ALERTS, CONTENTTYPES, DEVICES, DIALOGS, DIRECTIONS, CONTENTSNAME, LANGUAGES, RIGHTS, ROLES, ORDERS } from "./Constants";

export type EAlertType = (typeof ALERTS)[keyof typeof ALERTS];

export type EDialogType = (typeof DIALOGS)[keyof typeof DIALOGS];

export type ERolesType = (typeof ROLES)[keyof typeof ROLES];

export type ERightType = (typeof RIGHTS)[keyof typeof RIGHTS];

export type EActionType = (typeof ACTIONS)[keyof typeof ACTIONS];

export type EDirectionType = (typeof DIRECTIONS)[keyof typeof DIRECTIONS];

export type EContentNameType = (typeof CONTENTSNAME)[keyof typeof CONTENTSNAME];

export type EcontentType = (typeof CONTENTTYPES)[keyof typeof CONTENTTYPES];

export type EDeviceType = (typeof DEVICES)[keyof typeof DEVICES];

export type ELanguageType = (typeof LANGUAGES)[keyof typeof LANGUAGES];

export type EOrderType = (typeof ORDERS)[keyof typeof ORDERS];
