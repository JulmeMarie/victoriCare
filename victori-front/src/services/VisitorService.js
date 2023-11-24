import { HttpService } from "../redux/actions/http-action";
import { MONTHS, DAYS } from "../Utils/Static";
import { setVisitedContent } from "../redux/reducers/app-reducer";
import { VisitorItems } from "../Utils/Static";
const GET_ENDPOINT_VISITOR = "right-anm/visits/";
const PUT_ENDPOINT_VISITOR = "right-anm/visits";

export class VisitorService {
  data = {};

  static newInstance() {
    return new VisitorService();
  }

  init = (fmcValues, activeItems, activePeriod) => {
    if (fmcValues && fmcValues.length > 0 && !activeItems) {
      this.data.setActiveItems(VisitorItems);
      setTimeout(() => {
        this.list(activePeriod || "today");
      }, 500);
    }
  };

  togglePeriod = (activePeriod, activeItem) => {
    this.list(activePeriod);
  }

  setVisited = (visitedContents, item) => {
    if (!item || !visitedContents) return null;
    let type = item.split("-list")[0];
    if (!visitedContents.includes(type)) {
      if (
        [
          "home",
          "activity",
          "news",
          "post",
          "project",
          "organization",
          "donation",
        ].includes(type)
      ) {
        this.add(type);
      }
    }
  };

  add = async type => {
    HttpService.create(type, PUT_ENDPOINT_VISITOR, "JSON", false).then(
      response => {
        if (response.ok) {
          this.data.dispatch(setVisitedContent(type));
        } else {
          console.error(
            "Une erreur s'est produite lors de l'enregistrement de la visite"
          );
        }
      }
    );
  };

  list = async activePeriod => {
    HttpService.read(GET_ENDPOINT_VISITOR + activePeriod).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.setActivePeriod(activePeriod);
        this.data.setData(
          VisitorService.computeData(response.data, activePeriod)
        );
      } else {
        console.error("Echec lors du chargement des visites");
      }
    });
  };

  static computeData = (list, period) => {
    let data = [];
    let now = new Date();
    if (period === "today") {
      for (let i = 0; i <= 24; i++) {
        let singleVisitData = { period: i.toString().padStart(2, "0") + "h" };
        VisitorItems.forEach(item => {
          singleVisitData[item.value] = 0;
          list.forEach(visit => {
            if (
              visit.contentType === item.key &&
              new Date(visit.createAt).getHours() === i
            ) {
              singleVisitData[item.value] = singleVisitData[item.value] + 1;
            }
          });
        });
        data.push(singleVisitData);
      }
    } else if (period === "week") {

      let startDays = [];
      let endDays = [];
      DAYS.forEach(day => {
        day.numero <= now.getDay() ? endDays.push(day) : startDays.push(day);
      });

      startDays.concat(endDays).forEach(day => {
        //if(day.numero > now.getDay()) return;
        let singleVisitData = { period: day.str };
        VisitorItems.forEach(item => {
          singleVisitData[item.value] = 0;
          list.forEach(visit => {
            if (
              visit.contentType === item.key &&
              new Date(visit.createAt).getDay() === day.numero
            ) {
              singleVisitData[item.value] = singleVisitData[item.value] + 1;
            }
          });
        });
        data.push(singleVisitData);
      });
    }
    else if (period === "month") {
      const daysInMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate();
      console.log(daysInMonth);
      for (let i = 1; i <= daysInMonth; i++) {
        let singleVisitData = { period: i.toString().padStart(2, "0") };
        VisitorItems.forEach(item => {
          singleVisitData[item.value] = 0;
          list.forEach(visit => {
            if (
              visit.contentType === item.key &&
              new Date(visit.createAt).getDate() === i
            ) {
              singleVisitData[item.value] = singleVisitData[item.value] + 1;
            }
          });
        });
        data.push(singleVisitData);
      }
    }

    else if (period === "year") {

      let startMonths = [];
      let endMonths = [];
      MONTHS.forEach(month => {
        month.numero <= new Date().getMonth() ? endMonths.push(month) : startMonths.push(month);
      });
      startMonths.concat(endMonths).forEach(month => {
        let singleVisitData = { period: month.str };
        VisitorItems.forEach(item => {
          singleVisitData[item.value] = 0;
          list.forEach(visit => {
            if (
              visit.contentType === item.key &&
              new Date(visit.createAt).getMonth() === month.numero
            ) {
              singleVisitData[item.value] = singleVisitData[item.value] + 1;
            }
          });
        });
        data.push(singleVisitData);
      });
    }
    return data;
  };

  toggleGraph = (itemKey, activeItems) => {
    let computeItems = [...activeItems];
    if (VisitorService.isActive(itemKey, activeItems)) {
      computeItems = activeItems.filter(it => it.key !== itemKey);
    } else {
      computeItems.push(VisitorItems.find(it => it.key === itemKey));
    }
    this.data.setActiveItems(computeItems);
  };

  static isActive = (itemKey, activeItems) => {
    return activeItems.find(it => it.key === itemKey) !== undefined;
  };
}
