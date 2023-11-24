//import { updateCarousel, addCarousel, deleteCarousel, initCarousel } from "../redux/reducers/app-reducer";
import { setLoadingComments, setLoadedComments } from '../redux/reducers/app-reducer';
import { Dispatch } from "@reduxjs/toolkit";
import HttpService from "./HttpService";

const POST_ENDPOINT_CAROUSEL = "right-mod/carousel-item";
const PUT_ENDPOINT_CAROUSEL = "right-mod/carousel-item";
const DELETE_ENDPOINT_CAROUSEL = "right-mod/carousel-items/";
const GET_ENDPOINT_CAROUSEL = "right-anm/carousel-items";
const SLIDE_DURATION = 10000;
const RESPONSE_DURATION = 1000;

const comments = [
    {
        username: "VictoriCare",
        role: "Administrateur",
        message: "Bonjour, nous sommes heureux de partager avec vous les commentaires de nos utilisateurs. Merci",
        img: "0.png"
    },
    {
        username: "Samano CASTRE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        img: "1.jpg"
    },
    {
        username: "JULME MARIE WILNIE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        img: "2.jpg"
    },
    {
        username: "JULME MARIE WILNIQUE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        img: "3.jpg"
    },
    {
        username: "DEMOSTHERNE WOODELINE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        img: "4.jpg"
    },
    {
        username: "DEMOSTHERNE RAYMOND",
        role: "Baby Siter",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        img: "5.jpg"
    },
];


export class CommentService {

    private dispatch!: Dispatch;

    setDispatch = (dispatch: Dispatch) => {
        this.dispatch = dispatch;
    }

    initComment = () => {
        this.dispatch(setLoadingComments());
        HttpService.read("/api/Comments/1").then((response) => {
            response.isError = false;
            response.error = undefined;
            response.data = { list: comments }
            this.dispatch(setLoadedComments(response));
        });
    }

    /*

    initForm(carouselItemIn) {
        if (carouselItemIn) {
            this.data.setFormValues(carouselItemIn);
        } else {
            this.data.setFormValues({
                id: 0,
                title: "",
                description: "",
                imageData: "",
                order: 0,
            });
        }
        this.data.setLoading(false);
    }

    add() {
        HttpService.create(
            new FormData(this.data.formRef.current),
            PUT_ENDPOINT_CAROUSEL,
            "FORM-DATA",
            true
        ).then(response => {
            this.data.setLoading(false);
            if (response.ok) {
                this.data.dispatch(addCarousel(response.data));
                this.data.setResult({
                    type: ALERTS.SUCCESS,
                    message: "Ajout d'un item au carousel effectué.",
                });
                setTimeout(() => {
                    this.data.onClose();
                }, RESPONSE_DURATION);
            } else {
                this.data.setResult({
                    type: ALERTS.ERROR,
                    message:
                        "Ajout d'un item au carousel échoué. Prière de réessayer. Merci",
                });
            }
        });
    }

    update() {
        HttpService.update(
            new FormData(this.data.formRef.current),
            POST_ENDPOINT_CAROUSEL,
            "FORM-DATA",
            true
        ).then(response => {
            this.data.setLoading(false);
            if (response.ok) {
                this.data.dispatch(updateCarousel(response.data));
                this.data.setResult({
                    type: ALERTS.SUCCESS,
                    message: "Mise à jour du carousel éffectué",
                });
                setTimeout(() => {
                    this.data.onClose();
                }, RESPONSE_DURATION);
            } else {
                this.data.setResult({
                    type: ALERTS.ERROR,
                    message: "Mise à jour de carousel échoué. Prière de réessayer. Merci",
                });
            }
        });
    }

    delete(id) {
        this.data.setDialog({
            type: EDialogTypes.INFO,
            message: "Suppression en cours... Veuillez patienter. Merci",
        });

        HttpService.delete(DELETE_ENDPOINT_CAROUSEL + id, false).then(response => {
            if (!response.ok) {
                this.data.setDialog({
                    type: EDialogTypes.ALERT,
                    message:
                        "Echec de la suppression de l'item du carousel. Prière de réessayer. Merci",
                });
            } else {
                this.data.dispatch(deleteCarousel(id));
                this.data.setDialog(null);
            }
        });
    }

    deleteRequest(carouselItem) {
        this.data.stopTimerCallBack(true);
        this.data.setDialog({
            type: EDialogTypes.CONFIRM,
            message:
                "Êtes-vous sûr de vouloir supprimer l'item du carousel dont le titre est : \"<b>" +
                carouselItem.title +
                '"</b>',
            onConfirm: () => {
                this.delete(carouselItem.id);
            },
            onCancel: () => {
                this.data.setDialog(null);
                this.data.stopTimerCallBack(false);
            },
        });
    }

    list = async () => {
        return HttpService.read(GET_ENDPOINT_CAROUSEL).then(response => {
            if (response.ok) {
                this.data.dispatch(initCarousel(response.data));
            }
            else {
                console.error("Echec lors dela récupération des carousel items");
            }
        });
    }

    validateTextField(e, formValues) {
        this.data.setFormValues({
            ...formValues,
            [e.target.name]: e.target.value,
        });
    }

    validateFileChange(ref, formValues) {
        const [file] = ref.current.files;
        if (file) {
            this.data.setFormValues({
                ...formValues,
                [ref.current.name]: file,
                [ref.current.name.replace("m", "url")]: URL.createObjectURL(file),
            });
        }
    }

    submitForm(e) {
        e.preventDefault();
        this.data.setLoading(true);
        this.data.setResult(null);
        this.data.formRef.current.id.value > 0 ? this.update() : this.add();
    }

    initTimer(carouselItems, currentIndex, stopTimer, timer) {
        clearTimeout(timer.current);

        if (carouselItems != null && currentIndex >= carouselItems.length) {
            currentIndex = 0;
            this.data.setCurrentIndex(currentIndex);
        }
        if (!stopTimer) {
            timer.current = setTimeout(() => {
                if (carouselItems != null) {
                    this.data.setCurrentIndex(
                        currentIndex + 1 >= carouselItems.length ? 0 : currentIndex + 1
                    );
                }
            }, SLIDE_DURATION);
        }
    }

    changeCarouselItem(indice, timer, carouselItems, currentIndex) {
        clearTimeout(timer.current);
        let index = currentIndex + indice;

        if (index < 0) {
            index = carouselItems.length - 1;
        } else if (index >= carouselItems.length) {
            index = 0;
        }
        this.data.setCurrentIndex(index);
    }*/
}
