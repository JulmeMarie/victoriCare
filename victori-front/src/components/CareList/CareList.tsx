import React, { FC, useState } from 'react';
import './CareList.css';
import { mockCares } from '../../utils/global-default-values';
import DataTable from '../DataTable/DataTable';
import { IColumn } from '../../utils/datatable-interfaces';
import { isEqual } from '../../utils/global-util';

interface CareListProps { }
const CareList: FC<CareListProps> = () => {

  const entriesArr = [10, 20, 50, 100, 200, 500];
  const headerColumns = [
    { value: "Titre", key: "title", type: "string", minWidth: 250 },
    { value: "Enfant", key: "createFor", type: "string", minWidth: 250 },
    { value: "Date création", key: "createAt", type: "date", minWidth: 250 },
    { value: "Statut", key: "status", type: "string", minWidth: 200 },
    { value: "Auteur", key: "createBy", type: "string", minWidth: 250 },
  ];

  const headerExtraColumns = [
    { value: "Titre", key: "title", type: "string", minWidth: 250 },
    { value: "Déscription", key: "description", type: "string", minWidth: 300 },
    { value: "Date fin", key: "endDate", type: "date", minWidth: 200 },
    { value: "Durée", key: "during", type: "number", minWidth: 100 },
  ]

  const [rows, setRows] = useState<Array<Object>>([...mockCares]);

  const handleClick = (rows: Array<Object>) => {

    console.log("this element has been clicked")
    console.table(rows);
  }

  const loadData = () => {
    console.log("onLoadMoreRowsCallBack de CareListList");
    var testRows = [...mockCares];
    for (var i = 0; i < 1000; i++) {
      testRows = testRows.concat([...mockCares]);
    }
    setRows((rows) => rows.concat([...testRows]));
  }

  const handleDelete = (rowsToDelete: Array<Object>) => {
    console.log("this element has been deleted")
    const filtered = rows.filter(row => {
      return !rowsToDelete.find(r => isEqual(row, r));
    });
    setRows(filtered);
  }

  const handleReOrder = (header: { headerColumns: Array<IColumn>, extraColumns?: Array<IColumn> }) => {
    console.log("this element has been handleReOrder")
    console.table(rows)
  }

  const handleReOrganize = (header: { headerColumns: Array<IColumn>, extraColumns?: Array<IColumn> }) => {
    console.log("this element has been handleReOrganize")
    console.table(rows);
  }

  const handleEdit = (rows: Array<Object>) => {
    console.log("this element has been edited");
    console.table(rows)
  }

  return (
    <div className="CareList" data-testid="CareList">
      <DataTable
        UID="CareList"
        title={"List des soins"}
        tableConfig={{
          headerColumns,
          tableRows: rows,
          headerExtraColumns,
          isSearchable: true,
          isSortable: true,
          maxHeight: '25rem',
        }}

        columnConfig={{
          onReOrder: handleReOrder,
          onReOrganize: handleReOrganize
        }}

        colorConfig={{
          color: "rgb(255, 105, 180)",
          background: "rgba(249, 192, 192, 0.638)"
        }}

        onDeleteConfig={{
          isMultiRow: true,
          callBack: handleDelete
        }}

        onClickConfig={{
          isMultiRow: true,
          callBack: handleClick
        }}

        onEditConfig={{
          isMultiRow: true,
          callBack: handleEdit
        }}
        paginationConfig={{
          entry: entriesArr[0],
          entries: entriesArr
        }}
        onLoadMoreCallBack={loadData}
      />
    </div>
  );
}

export default CareList;
