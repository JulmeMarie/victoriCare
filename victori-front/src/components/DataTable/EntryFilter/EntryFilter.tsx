import React, { FC } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateEntry, updatePage } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import './EntryFilter.css';
import { IColorConfig } from '../../../utils/datatable-interfaces';
import { DEFAULTENTRIES } from '../../../utils/datatable-constants';

interface EntryFilterProps {
  UID: string,
  colorConfig: IColorConfig
}

const EntryFilter: FC<EntryFilterProps> = ({ UID, colorConfig }) => {

  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const entries = datatable.pagination?.config?.entries || DEFAULTENTRIES;
  const entry = datatable.pagination?.config?.entry || entries[0];
  const dispatch = useDispatch<AppDispatch>();

  console.log("EntryFilter");

  const handleChange = (entry: number) => {
    dispatch(updateEntry({ UID, entry }));
    dispatch(updatePage({ UID }));
  }

  return (
    <div className="EntryFilter"
      data-testid="EntryFilter"
      onClick={(event) => { event.stopPropagation() }}>
      <label htmlFor="entries">Entries</label>
      <select
        onChange={(event) => handleChange(Number(event.target.value))}
        id="entries"
        style={{ border: `solid ${colorConfig.background}  0.1rem`, color: colorConfig.color }}
        defaultValue={entry}
      >
        {entries.map((optValue, index) => <option key={index} value={optValue}>{optValue}</option>)}
      </select>
    </div>
  );
}

export default EntryFilter;
