import React, { FC } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { performSearch, updatePage } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import './SearchFilter.css';
import { IColorConfig } from '../../../utils/datatable-interfaces';

interface SearchFilterProps {
  UID: string,
  colorConfig: IColorConfig
}
const SearchFilter: FC<SearchFilterProps> = ({ UID, colorConfig }) => {
  const search = useSelector((state: AppState) => state.datatableReducer[UID])?.search;
  const dispatch = useDispatch<AppDispatch>();
  console.log("SearchFilter");

  const handleChange = (value: string) => {
    if (search !== value) {
      dispatch(performSearch({ UID, search: value }));
      dispatch(updatePage({ UID }));
    }
  }

  return (
    <div className="SearchFilter"
      data-testid="SearchFilter"
      onClick={(event) => { event.stopPropagation() }}>
      <label htmlFor="search">Search: </label>
      <input
        type="text" onChange={(e) => handleChange(e.target.value)}
        id="search" value={search}
        style={{ border: `solid ${colorConfig.background} 0.1rem` }} />
    </div>
  );
}

export default SearchFilter;