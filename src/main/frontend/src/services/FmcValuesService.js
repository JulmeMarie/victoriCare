import {IconTypes} from "../Utils/Static";
import {EFMCValuesTypes} from "../Utils/Enums";
import {HttpService} from "./HttpService";
import {EAlertTypes} from "../Utils/Enums";
import {updateFmcValues, initFmcValues} from "../redux/reducer";

const POST_ENDPOINT_FMC = "right-mod/fmc-values";
const PUT_ENDPOINT_FMC = "right-mod/fmc-values";
const DELETE_ENDPOINT_FMC = "right-mod/fmc-values/";
const READ_ALL_ENDPOINT_FMC = "right-anm/fmc-values";

export class FmcValuesService {
  data = {};

  static newInstance() {
    return new FmcValuesService();
  }

  initFmcValues = (localType, type) => {
    if (this.data.fmcValues) {
      this.data.setFormValues(
        FmcValuesService.getByType([...this.data.fmcValues], type)
      );
    }
    if (type !== localType) {
      this.data.setResult(null);
      this.data.setLocalType(type);
    }
  };

  list = async () => {
    return HttpService.read(READ_ALL_ENDPOINT_FMC).then(response => {
      if (response.ok) {
        this.data.dispatch(initFmcValues(response.data));
      } else {
        console.error("Echec de la récupération des fmc values");
      }
    });
  };

  add = async fmcValues => {
    return HttpService.create(
      JSON.stringify(fmcValues),
      PUT_ENDPOINT_FMC,
      "JSON",
      true
    );
  };

  update = async fmcValues => {
    return HttpService.update(
      JSON.stringify(fmcValues),
      POST_ENDPOINT_FMC,
      "JSON",
      true
    );
  };

  deleteById = async id => {
    return HttpService.delete(DELETE_ENDPOINT_FMC + id, true);
  };

  static getIcon = type => {
    let icon = IconTypes.find(t => t.type && t.type === type);
    return icon ? icon.icon : "";
  };

  static getByType = (fmcValues, type) => {
      if ([EFMCValuesTypes.Label, EFMCValuesTypes.Site].includes(type.toUpperCase())) {
        return fmcValues.filter(
          fmcValue =>
            fmcValue.type &&
            fmcValue.type.toUpperCase() === type.toUpperCase()
        );
      } else {
        return fmcValues.filter(
          fmcValue =>
            fmcValue.type &&
            fmcValue.type.toUpperCase() !== EFMCValuesTypes.Label
        );
      }
  };

  static buttonsTypeToAdd = data => {
    return IconTypes.filter(
      icon =>
        icon.type.toUpperCase() !== "LABEL" &&
        data.filter(d => d.type === icon.type).length === 0
    );
  };

  static getByKey = (fmcValues, key) => {
    let label = fmcValues.find(
      fmcValue =>
        fmcValue.key && fmcValue.key.toLowerCase() === key.toLowerCase()
    );
    return label ? label.text : "Label Not Found";
  };

  static getById = (fmcValues, id) => {
    return fmcValues.find(fmcValue => fmcValue.id === id);
  };

  toggleInput = (formValues, id) => {
    let list = formValues.map(fmcValue => {
      let copy = {...fmcValue};
      if (fmcValue.id === id) {
        copy.active = !fmcValue.active;
      }
      return copy;
    });
    this.data.setFormValues(list);
  };

  validateTextField = (e, formValues, id) => {
    let filteredElements = formValues.map(fmcValue => {
      let copyItem = {...fmcValue};
      if (fmcValue.id === id) {
        copyItem.text = e.target.value;
      }
      return copyItem;
    });
    this.data.setFormValues(filteredElements);
  };

  submitForm = (e, formValues, localType) => {
    e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);
    const fmcValuesPromises = [];
    FmcValuesService.getByType([...this.data.fmcValues], localType).forEach(
      originalValue => {
        //get this item from form values
        let currentValue = FmcValuesService.getById(
          formValues,
          originalValue.id
        );
        if (currentValue) {
          if (currentValue.text.trim().length === 0 && currentValue.active) {
            currentValue.active = false;
          }
          if (
            currentValue.text !== originalValue.text ||
            currentValue.active !== originalValue.active
          ) {
            fmcValuesPromises.push(this.update(currentValue));
          }
        }
      }
    );

    Promise.all(fmcValuesPromises).then(() => {
      this.data.dispatch(updateFmcValues(formValues));
      this.data.setLoading(false);
      this.data.setResult({
        type: EAlertTypes.SUCCESS,
        message: "Mise à jour effectuée",
      });
    });
  };
}
