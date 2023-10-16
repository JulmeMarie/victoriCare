import {HttpService} from "./HttpService";
import {EAlertTypes, ERightTypes} from "../Utils/Enums";
import {updateMenu, initMenu} from "../redux/reducer";

const POST_ENDPOINT_MENU = "right-mod/menu-items";
const READ_ALL_ENDPOINT_MENU = "right-anm/menu-items";

export class MenuService {
  data = {};

  static newInstance() {
    return new MenuService();
  }

  initMenu = (menuRx, onlineUser) => {
    if (onlineUser && menuRx && menuRx.length > 0) {
      let filteredMenu = this.conputeMenu(menuRx, onlineUser.id > 0).filter(item => {
        
        if(
            (item.head && item.head.type.includes("dashboard")) ||
            (item.type && item.type.includes("dashboard")) || 
            (item.href && item.href.includes("dashboard"))
          ) {
              if(item.href && (item.href.includes("home") || item.href.includes("visitors-stat") || item.href.includes("my-profil") || item.href.includes("logout"))) {
                  return true;
              }
              else if(item.href && (item.href.includes("our-team") || 
              item.href.includes("our-members") || 
              item.href.includes("donation") || 
              item.href.includes("mention") ||
              item.href.includes("partner")) && 
              onlineUser.right !== ERightTypes.ADMINISTRATOR.key) {
                return false
              }
              else {
                return onlineUser.right === ERightTypes.ADMINISTRATOR.key || onlineUser.right === ERightTypes.MODERATOR.key;
              }
          }
          return true;
      });
      this.data.setMenu(filteredMenu);
    }
  };

  initForm = (menuRx, type, localType) => {
    let list = [...menuRx].filter(
      item => item.type === type || item.type === "dashboard-" + type
    );
    this.data.setFormMenu(list);
    this.data.originalMenu = list;
    if (type !== localType) {
      this.data.setResult(null);
      this.data.setLocalType(type);
    }
  };

  update = async formValues => {
    return HttpService.update(
      JSON.stringify(formValues),
      POST_ENDPOINT_MENU,
      "JSON",
      true
    );
  };

  list = async () => {
    return HttpService.read(READ_ALL_ENDPOINT_MENU).then(response => {
      if (response.ok) {
        this.data.dispatch(initMenu(response.data));
      } else {
        console.error("Echec lors de la récupération des menus items");
      }
    });
  };

  static getItem = (labels, key) => {
    let label = labels.find(label => label.key === key);
    return label ? label.value : "404 Not Found";
  };

  static getById = (menu, id) => {
    return menu.find(item => item.id === id);
  };

  conputeMenu = (menuRx, isOnline) => {
    let nav = [];
    let group = {
      head: "",
      items: [],
    };

    let navMenu = menuRx.filter(menu => menu.type === this.data.type);
    navMenu.forEach(navItem => {
      if (!navItem.group || navItem.group.length === 0) {
        if (group.items.length > 0) {
          let addedGroup = {...group};
          nav.push(addedGroup);
          group.head = "";
          group.items = [];
        }
        nav.push(navItem);
      } else {
        if (navItem.href.includes("login") || navItem.href === "/dashboard") {
          if (
            (navItem.href.includes("login") && !isOnline) ||
            (navItem.href === "/dashboard" && isOnline)
          ) {
            navItem.href ? group.items.push(navItem) : (group.head = navItem);
          }
        } else {
          navItem.href ? group.items.push(navItem) : (group.head = navItem);
        }
      }
    });

    if (group.items.length > 0) {
      nav.push(group);
    }
    return nav;
  };

  validateTextField = (e, formMenu, id) => {
    let list = formMenu.map(item => {
      let copyItem = {...item};
      if (item.id === id) {
        copyItem.text = e.target.value;
      }
      return copyItem;
    });
    this.data.setFormMenu(list);
  };

  submitForm = (e, formMenu) => {
    e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);
    const menuPromises = [];
    this.data.originalMenu.forEach(originalValue => {
      let currentValue = MenuService.getById(formMenu, originalValue.id);
      if (currentValue && currentValue.text !== originalValue.text) {
        menuPromises.push(this.update(currentValue));
      }
    });
    Promise.all(menuPromises).then(() => {
      this.data.dispatch(updateMenu(formMenu));
      this.data.setLoading(false);
      this.data.setResult({
        type: EAlertTypes.SUCCESS,
        message: "Mise à jour menu éffectuée",
      });
    });
  };
}
