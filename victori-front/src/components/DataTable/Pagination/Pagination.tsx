import React, { FC } from 'react';
import { FaChevronLeft, FaChevronRight, FaSearchPlus } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { setPageIndex, updatePage } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import './Pagination.css';
import { DEFAULTENTRIES } from '../../../utils/datatable-constants';

interface PaginationProps {
  UID: string,
}

const Pagination: FC<PaginationProps> = ({ UID }) => {

  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const isDisplayContextMenu = datatable.contextMenu?.display;

  const totalRows = datatable.table?.config?.tableRows?.length || 0;

  const entry = datatable.pagination?.config?.entry || DEFAULTENTRIES[0];
  const filteredRowIndexes = datatable.table?.indexes.filteredRowIndexes;
  const tableRowIndexes = datatable.table?.indexes.tableRowIndexes;

  const pageIndex = datatable.pagination?.pageIndex || 0;
  const pages = datatable.pagination?.pages || [1];
  const pageRowIndexes = datatable.table?.indexes.pageRowIndexes;
  const loadMore = datatable.callBacks?.onLoadMoreCallBack;
  const MAX_DISPLAYING_PAGES = 10;

  console.log("Pagination");

  const handleChange = (event: React.MouseEvent<HTMLElement>, pageIndex: number) => {
    if (!isDisplayContextMenu) {
      event.stopPropagation();
      dispatch(setPageIndex({ UID, pageIndex }));
      dispatch(updatePage({ UID }));
    }
  }

  const onMoreDataClick = (event: React.MouseEvent<HTMLElement>) => {
    if (!isDisplayContextMenu) {
      event.stopPropagation();
      loadMore && loadMore();
    }
  }

  const getInfos = () => {
    let infos = "Showing ";
    infos += pageIndex * entry + 1; //start element
    infos += " to " + (pageIndex * entry + (pageRowIndexes?.length || 0)) + " of " + (filteredRowIndexes?.length || tableRowIndexes.length) + " entries ";//number of entries
    infos += datatable.search ? " (filtered from " + totalRows + " total entries)" : "";//entries filtered
    return infos;
  }

  const getSlicedPages = () => {
    const start = Math.floor(pageIndex / MAX_DISPLAYING_PAGES) * MAX_DISPLAYING_PAGES;
    const end = start + MAX_DISPLAYING_PAGES;
    return pages.slice(start, end);
  }

  return (
    <div className="Pagination" data-testid="Pagination">
      {pageRowIndexes?.length && <>
        <div className='pagination__info'>
          {getInfos()}
        </div>
        <div className='pagination__tab'>
          <div
            className={`arrow ${pageIndex > 0 && ' page-index'} `}
            onClick={(event) => handleChange(event, pageIndex - 1)}>
            <FaChevronLeft />
          </div>
          {
            getSlicedPages().map((page) =>
              <div
                className={`page-index ${pageIndex + 1 === page && ' current-page'}`}
                key={page}
                onClick={(event) => handleChange(event, page - 1)}>{page}
              </div>
            )
          }
          <div
            className={`arrow ${pageIndex + 1 < pages.length && ' page-index'}`}
            onClick={(event) => handleChange(event, pageIndex + 1)}>
            <FaChevronRight />
          </div>
          {
            loadMore &&
            <div className='page-index load-more-icon' onClick={(event) => onMoreDataClick(event)}>
              <FaSearchPlus />
            </div>
          }
        </div></>}
    </div>
  );
}

export default Pagination;