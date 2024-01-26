import React, { FC, useState } from 'react';
import './EventList.css';
import { mockCares } from '../../utils/global-default-values';
import DataTable from '../DataTable/DataTable';
import { isEqual } from '../../utils/global-util';
import { ICare } from '../../utils/global-interfaces';

interface EventListProps { }

const EventList: FC<EventListProps> = () => {
  const entriesArr = [5, 10, 20, 50, 100];
  const headerColumns = [
    { value: "Titre", key: "title", type: "string", minWidth: 28 },
    { value: "Enfant", key: "createFor", type: "string", minWidth: 20 },
    { value: "Date création", key: "createAt", type: "date", minWidth: 20 },
    { value: "Statut", key: "status", type: "string", minWidth: 12 },
    { value: "Auteur", key: "createBy", type: "string", minWidth: 20 },
  ];

  const headerExtraColumns = [
    { value: "Déscription", key: "description", type: "string", minWidth: 20 },
    { value: "Date fin", key: "endDate", type: "date", minWidth: 11 },
    { value: "Durée", key: "during", type: "number", minWidth: 8 },
  ]

  const [rows, setRows] = useState<Array<Object>>([...mockCares]);

  const handleClick = (rows: Array<Object>) => {
    console.log("this element has been clicked")
    console.table(rows)
  }

  const loadData = () => {
    console.log("onLoadMoreRowsCallBack de CareListList")
    setRows((rows) => rows.concat([...mockCares]));
  }

  const handleDelete = (rows: Array<Object>) => {
    console.log("this element has been deleted")
    console.table(rows)
  }

  const handleEdit = (rows: Array<Object>) => {
    console.log("this element has been edited");
    console.table(rows);
  }

  return (
    <div className="EventList" data-testid="EventList">
      <DataTable
        UID="CareList"
        title={"List des événements"}
        tableConfig={{
          headerColumns,
          tableRows: rows,
          headerExtraColumns,
          isSearchable: true,
          isSortable: true
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

export default EventList;
