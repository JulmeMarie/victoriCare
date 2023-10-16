import { HttpService } from "./HttpService";
import {updateContact, addContact, initContacts, deleteContact} from "../redux/reducer";
import { EAlertTypes, EDialogTypes } from "../Utils/Enums";
const POST_ENDPOINT_CONTACT = "right-adm/contact";
const PUT_ENDPOINT_CONTACT = "right-anm/contact";
const GET_ENDPOINT_CONTACT = "right-adm/contacts";
const DELETE_ENDPOINT_CONTACT  = "right-adm/contacts/";
const RESPONSE_WAITE_DURATION = 1500;

export class ContactService {
    data = {};

    static newInstance = () => {
        return new ContactService();
    };

    initForm = (contact) => {
      this.data.setFormValues(contact === null ? {} : contact);
      this.data.setLoading(false);
    }

    list = async () => {
        HttpService.read(GET_ENDPOINT_CONTACT).then((response) => {
            this.data.setLoading(false);
            if(response.ok) {
                this.data.dispatch(initContacts(response.data));
            }
            else {
                this.data.setResult({
                    type: EAlertTypes.ERROR,
                    message:
                      "Une erreur s'est produite lors de la récupération des messages. Merci de réessayer.",
                  });
            }
        });
    };


    add = async (formValues) => {
        HttpService.create(JSON.stringify(formValues), PUT_ENDPOINT_CONTACT, "JSON", true).then(response => {
            this.data.setLoading(false);
            if(response.ok) {
                this.data.dispatch(addContact(response.data));
                this.data.setFormValues({});
                this.data.setResult({
                    type: EAlertTypes.SUCCESS,
                    message : "Message envoyé avec succès"
                });
            }
            else {
                this.data.setResult({
                    type: EAlertTypes.ERROR,
                    message : "Echec d'envoi du message. Merci de réessayer."
                });
            }
          });
    };


    update = async (formValues) => {
        return HttpService.update(JSON.stringify(formValues),POST_ENDPOINT_CONTACT, "JSON", true).then(response => {
            this.data.setLoading(false);
            if(response.ok) {
                this.data.setFormValues({});
                this.data.setResult({
                    type: EAlertTypes.SUCCESS,
                    message : "Réponse envoyé avec succès"
                });
                setTimeout(() => {
                    this.data.dispatch(updateContact(response.data));
                }, RESPONSE_WAITE_DURATION);
            }
            else {
                this.data.setResult({
                    type: EAlertTypes.ERROR,
                    message : "Echec d'envoi de la réponse. Merci de réessayer."
                });
            }
          });
    };

    delete(id) {
        this.data.setDialog({
          type: EDialogTypes.INFO,
          message: "Suppression en cours... Veuillez patienter. Merci",
        });
    
        HttpService.delete(DELETE_ENDPOINT_CONTACT + id, false).then(response => {
          if (!response.ok) {
            this.data.setDialog({
              type: EDialogTypes.ALERT,
              message:
                "Echec de la suppression du message. Prière de réessayer. Merci",
            });
          } else {
            this.data.dispatch(deleteContact(id));
            this.data.setDialog(null);
          }
        });
      }

    submitForm = async (e,formValues, isContact) => {
        e.preventDefault();
        this.data.setResult(null);
        this.data.setLoading(true);
        isContact ? this.add(formValues) : this.update(formValues);
    }

    validateTextField(e, formValues) {
        this.data.setFormValues({
          ...formValues,
          [e.target.name]: e.target.value,
        });
    }

    deleteRequest(contact) {
        this.data.setDialog({
          type: EDialogTypes.CONFIRM,
          message:
            "Êtes-vous sûr de vouloir supprimer le message de  \"<b>" +
            contact.name +
            '"</b>',
          onConfirm: () => {
            this.delete(contact.id);
          },
          onCancel: () => {
            this.data.setDialog(null);
          },
        });
      }
}