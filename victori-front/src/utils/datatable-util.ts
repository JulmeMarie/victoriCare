import { IColumn } from "./datatable-interfaces";
import { KeyPair } from "./global-interfaces";

export const TYPES = {
    DATE: "date",
    NUMBER: "number",
    STRING: "string"
}

export const getValueArray = (row: Object, column: string): Array<KeyPair> | undefined => {
    type Key = keyof typeof row;
    const key = column as Key;
    if (row[key] !== undefined) {
        const value = row[key] as Object;
        return value as Array<KeyPair>
    }
    return undefined;
}

export const getValueStr = (object: Object, target: string): String => {
    type Key = keyof typeof object;
    const key = target as Key;
    return String(object[key]);
}

export const getValue = (row: Object, column: IColumn): String | Number | Date => {
    type Key = keyof typeof row;
    const key = column.key as Key;

    return column.type === TYPES.DATE ?
        new Date(row[key].toString()) : column.type === TYPES.NUMBER ?
            Number(row[key]) : String(row[key]);
}

export const getObjectValueStr = (row: Object, column: IColumn): String => {
    const value = getValue(row, column);
    return column.type === TYPES.DATE ? value.toLocaleString() : String(value);
}

export const computePages = (nbRows: number, nbRowsPerPage: number) => {
    let num = Math.ceil(nbRows / nbRowsPerPage);
    let arr = [];
    for (var i = 1; i <= num; i++) { arr.push(i); }
    return arr;
}

export const defaultColorConfig = {
    color: "#4E4D4E",/*"#5E5C5E",*"#635F63", "rgb(114, 109, 109)",*/
    background: "#f6f8fa",/*"#f9f9f9"*/
}

export const getSlicedRows = (rows: Array<number>, pageIndex: number, entry: number): Array<number> => {
    const startIndex = pageIndex * entry;
    const endIndex = (pageIndex + 1) * entry;
    return rows.slice(startIndex, endIndex);
}