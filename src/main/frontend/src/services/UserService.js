import {EUserTypes} from "../Utils/Enums";
import {HttpService} from "./HttpService";
import {EDialogTypes, EAlertTypes} from "../Utils/Enums";
import {updateUser, deleteUser, initUsers, setOnlineUser} from "../redux/reducer";
import {FmcValuesService} from "./FmcValuesService";

const POST_ENDPOINT_USER = "right-usr/user";
const PUT_ENDPOINT_USER = "right-adm/user";
const DELETE_ENDPOINT_USER = "right-adm/user/";
const GET_ENDPOINT_USER = "right-anm/users";
const UPDATE_PASSWORD_ENDPOINT_USER = "right-anm/update/password";
const UPLOAD_PHOTO_ENDPOINT_USER = "right-usr/user/photo";
const VALIDATE_ENDPOINT_USER = "right-anm/validation";

const emptyUser = {
  id: 0,
  username: "",
  firstname: "",
  lastname: "",
  isContactAddressee: false,
  role: 0,
  description: "",
  right: 0,
  email: "",
  image: "",
};

export class UserService {
  data = {};

  static newInstance() {
    return new UserService();
  }

  initList = (users, fmcValues, type) => {
    if (users && users.length > 0) {
      this.data.setData(UserService.getByType(users, type));
      this.data.setTitle(
        FmcValuesService.getByKey(fmcValues, type + "-list-key")
      );
    }
    this.data.setLoading(false);
  };

  initForm = user => {
    if (user === null) {
      this.data.setFormValues(emptyUser);
    }
    else {
      this.data.setFormValues(user);
    }
    this.data.setLoading(false);
  };

  initProfilForm = user => {
    if (user !== null) {
      this.data.setFormValues({
        urlFile: user.urlFile,
        mFile: user.mFile,
      });
      this.data.setLoading(false);
    }
  };

  list = async () => {
    HttpService.read(GET_ENDPOINT_USER).then(response => {
      if (response.ok) {
        this.data.dispatch(initUsers(response.data));
      } else {
        console.error("Echec lors de la récupération des users");
      }
    });
  };

  add = async form => {
    HttpService.create(
      new FormData(form),
      PUT_ENDPOINT_USER,
      "FORM-DATA",
      false
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Ajout d'un nouveau membre effectué.",
        });
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message:
            "Ajout d'un nouveau membre échoué. Prière de réessayer. Merci",
        });
      }
    });
  };

  update = async form => {
    HttpService.update(
      new FormData(form),
      POST_ENDPOINT_USER,
      "FORM-DATA",
      true
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.dispatch(updateUser(response.data));
        if(form.onlineUserId.value === form.id.value) {
          this.data.dispatch(setOnlineUser(response.data));
        }
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Mise à jour du compte éffectué",
        });
        /*
        setTimeout(() => {
          this.data.onClose();
        }, RESPONSE_DURATION);*/
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message: "Mise à jour du compte échoué. Prière de réessayer. Merci",
        });
      }
    });
  };

  updatePassword = async (password, type, code, id) => {
    this.data.setResult(null);
    return HttpService.update(
      JSON.stringify({
        id: id,
        code: code,
        type: type,
        password: password,
      }),
      UPDATE_PASSWORD_ENDPOINT_USER,
      "JSON",
      true
    );
  };

  validate = async (id, code, type) => {
    this.data.setResult(null);
    return HttpService.update(
      JSON.stringify({
        id: id,
        code: code,
        type: type,
      }),
      VALIDATE_ENDPOINT_USER,
      "JSON",
      true
    );
  };

  delete(id) {
    this.data.setDialog({
      type: EDialogTypes.INFO,
      message: "Suppression en cours... Veuillez patienter. Merci",
    });

    HttpService.delete(DELETE_ENDPOINT_USER + id, false).then(response => {
      if (!response.ok) {
        this.data.setDialog({
          type: EDialogTypes.ALERT,
          message:
            "Echec de la suppression de l'item du User. Prière de réessayer. Merci",
        });
      } else {
        this.data.dispatch(deleteUser(id));
        this.data.setDialog(null);
      }
    });
  }

  deleteRequest(user) {
    this.data.setDialog({
      type: EDialogTypes.CONFIRM,
      message:
        'Êtes-vous sûr de vouloir supprimer : "<b>' +
        user.lastname +
        " " +
        user.firstname +
        '"</b>',
      onConfirm: () => {
        this.delete(user.id);
      },
      onCancel: () => {
        this.data.setDialog(null);
      },
    });
  }

  static getByType = (users, type) => {
    if (!users) return null;

    if (type.toUpperCase() === EUserTypes.MEMBER) {
      return users.filter(
        user => user.role.toUpperCase() === EUserTypes.MEMBER
      );
    } else if (type.toUpperCase() === EUserTypes.TEAM) {
      return users.filter(
        user => user.role.toUpperCase() !== EUserTypes.MEMBER
      );
    } else {
      return users.filter(
        user => user.role.toUpperCase() === type.toUpperCase()
      );
    }
  };

  validateTextChange = (e, formValues) => {
    this.data.setFormValues({...formValues, [e.target.name]: e.target.value});
  };

  toggleCheckboxChange = (e, formValues) => {
    this.data.setFormValues({...formValues, [e.target.name]: e.target.checked});
  };

  validateFileChange = (ref, formValues) => {
    const [file] = ref.current.files;
    if (file) {
      this.data.setFormValues({
        ...formValues,
        [ref.current.name]: file,
        [ref.current.name.replace("m","url")]: URL.createObjectURL(file),
      });
    }
  };

  submitPhotoProfilForm = (e, imageRef) => {
    e.preventDefault();
    this.data.setResult(null);
    this.data.setLoading(true);
    const [file] = imageRef.current.files;
    const formData = new FormData();
    formData.append("mFile", file);
    HttpService.update(
      formData,
      UPLOAD_PHOTO_ENDPOINT_USER,
      "FORM-DATA",
      true
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.dispatch(updateUser(response.data));
        this.data.dispatch(setOnlineUser(response.data));
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Votre photo de profil a été mise à jour.",
        });
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message: "Mise à jour photo profil échoué.",
        });
      }
    });
  };

  subSubmitForm = e => {
    e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);
    if (this.data.formRef.current.id.value > 0) {
      this.update(this.data.formRef.current);
    } else {
      this.add(this.data.formRef.current);
    }
  };
}
