import {HttpService} from "./HttpService";
import {EDialogTypes, EAlertTypes} from "../Utils/Enums";
import {
  updateContent,
  addContent,
  deleteContent,
  initContents,
} from "../redux/reducer";

const POST_ENDPOINT_CONTENT = "right-mod/content";
const PUT_ENDPOINT_CONTENT = "right-mod/content";
const DELETE_ENDPOINT_CONTENT = "right-mod/contents/";
const READ_ALL_ENDPOINT_CONTENT = "right-anm/contents-by-type/all";

export class ContentService {
  data = {};
  static emptySection = {
    id: 0,
    title: "",
    text: "",
    leftMFile: "",
    rightMFile: "",
    folder: "",
    contentId: "",
    order: "",
    htmlClass: "",
  };

  static newInstance() {
    return new ContentService();
  }

  initForm = contentIn => {
    if (contentIn) {
      let orderedSections = ContentService.getOrderedSections(contentIn);
      let copy = {...contentIn}
      copy.sections = orderedSections;
      
      this.data.setFormValues(copy);
    } else {
      this.data.setFormValues({
        id: 0,
        title: "",
        sections: [],
      });
    }
    this.data.setLoading(false);
  };

  list = async () => {
    return HttpService.read(READ_ALL_ENDPOINT_CONTENT).then(response => {
      if (response.ok) {
        this.data.dispatch(initContents(response.data));
      } else {
        console.log("Erreur lors de récupération des contents.");
      }
    });
  };

  add = async () => {
   
    HttpService.create(
      new FormData(this.data.formRef.current),
      PUT_ENDPOINT_CONTENT,
      "FORM-DATA",
      true
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.dispatch(addContent(response.data));
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Ajout d'un item au carousel effectué.",
        });
        /*
        setTimeout(() => {
          this.data.onClose();
        }, ON_CLOSE_DURATION);*/
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message:
            "Ajout d'un item au carousel échoué. Prière de réessayer. Merci",
        });
      }
    });
  };

  update = async () => {
    HttpService.update(
      new FormData(this.data.formRef.current),
      POST_ENDPOINT_CONTENT,
      "FORM-DATA",
      true
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.dispatch(updateContent(response.data));
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Mise à jour du contenu éffectué",
        });
        /*
        setTimeout(() => {
          this.data.onClose();
        }, ON_CLOSE_DURATION);*/
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message: "Mise à jour de contenu échoué. Prière de réessayer. Merci",
        });
      }
    });
  };

  delete = async id => {
    this.data.setDialog({
      type: EDialogTypes.INFO,
      message: "Suppression en cours... Veuillez patienter. Merci",
    });

    HttpService.delete(DELETE_ENDPOINT_CONTENT + id, false).then(response => {
      if (!response.ok) {
        this.data.setDialog({
          type: EDialogTypes.ALERT,
          message:
            "Echec de la suppression du contenu. Prière de réessayer. Merci",
        });
      } else {
        this.data.dispatch(deleteContent(id));
        this.data.setDialog(null);
      }
    });
  };

  static getOneByType = (contents, type) => {
    let item = null;
    if (type !== null && contents !== null) {
      item = contents.find(content => content.type.toLowerCase() === type.toLowerCase());
    }
    return item;
  };

  static getById = (contents, id) => {
    let item = null;
    if (contents != null && id != null) {
      item = contents.find(content => content.id === parseInt(id));
    }
    return item;
  };

  static getByType = (contents, type) => {
    if (type !== null && contents !== null) {
      return contents.filter(
        content => content.type.toLowerCase() === type.toLowerCase()
      );
    }
    return null;
  };

  initList = (type, id) => {
    let contentType = type === null ? id : type;
    if (contentType && this.data.contents && this.data.contents.length > 0) {
      this.data.setContentsType(contentType);
      this.data.setData(ContentService.getByType(this.data.contents, contentType));
    }
  }

  initContent = (contents, id) => {
    if (contents && contents.length > 0) {
      if (this.data.type) {
        this.data.setContent(
          ContentService.getOneByType(contents, this.data.type)
        );
      }
      if (id) {
        this.data.setContent(ContentService.getById(contents, id));
      }
    }
  };

  static getOrderedSections = content => {
    let sections = [...content.sections];
    sections.sort((a, b) => a.order - b.order);
    return sections;
  };

  deleteRequest = (content) => {
    this.data.setDialog({
      type: EDialogTypes.CONFIRM,
      message:
        "Êtes-vous sûr de vouloir supprimer le contenu dont le titre est : \"<b>" +
        content.title +
        '"</b>',
      onConfirm: () => {
        this.delete(content.id);
      },
      onCancel: () => {
        this.data.setDialog(null);
      },
    });
  };
  static getCardImage = content => {
    if(!content.sections || content.sections.length === 0) return null;
    return content.sections[0].leftUrlFile != null
      ? content.sections[0].leftUrlFile
      : content.sections[0].rightUrlFile;
  };

  static getCardDescription = content => {
    if(!content.sections || content.sections.length === 0) return "AUCUNE DESCRIPTION";
    return content.sections[0].text
  };

  static getCardImageAlt = content => {
    if(ContentService.getCardImage(content)) return content.sections[0].title;
    return "AUCUNE IMAGE"

  }

  validateTextField = (ref, formValues) => {
    
    this.data.setFormValues({
      ...formValues,
      [ref.current.name]: ref.current.value,
    });
  };

  oneMoreSection = (order, formValues) => {
    let updateContent = {...formValues};
    let maxId = 0;
    let list = updateContent.sections.map(section => {
      let copy = {...section}
      if (copy.order > order) {
        copy.order = copy.order + 1;
      }
      maxId = copy.id > maxId ? copy.id : maxId;
      return copy;
    });

    list.push({
      ...ContentService.emptySection,
      id: maxId + 1,
      order: order + 1,
    });
    list.sort((a, b) => a.order - b.order);
    updateContent.sections = list;
    this.data.setFormValues(updateContent);
  };

  onRemoveSection = (section, formValues) => {
    let updateContent = {...formValues};
    let list = updateContent.sections.map(item => {
      const copy = {...item}
      if (copy.order > section.order) {
        copy.order = copy.order - 1;
      }
      return copy;
    });

    updateContent.sections = list.filter(p => p.id !== section.id);
    this.data.setFormValues(updateContent);
  };

  onSectionChange = (section, formValues) => {
    let filteredSections = formValues.sections.map(item =>  item.id === section.id ? section : item);
    formValues.sections = filteredSections;
    this.data.setFormValues({...formValues});
  };

  submitForm = (e, formValues) => {
    e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);
    this.data.formRef.current.id.value > 0
      ? this.update()
      : this.add();
  };

  static initTextAreaSize = (section, setWidthCallback) => {
    if (section) {
      if (section.leftUrlFile && section.rightUrlFile) setWidthCallback(50);
      else if (section.leftUrlFile || section.leftUrlFile) setWidthCallback(75);
    }
  };

  static validateSectionTextField = (ref, section, onSectionChangeCallback) => {
    onSectionChangeCallback({
      ...section,
      [ref.current.name.split(".")[1]]: ref.current.value,
    });
  };
  static validateSectionFileChangeHandler = (
    ref,
    section,
    onSectionChangeCallback
  ) => {
    const [file] = ref.current.files;
    if (file) {
      let name =
        ref.current.name.split(".")[1] === "leftMFile"
          ? "leftUrlFile"
          : "rightUrlFile";
      onSectionChangeCallback({...section, [name]: URL.createObjectURL(file)});
    }
  };
}
