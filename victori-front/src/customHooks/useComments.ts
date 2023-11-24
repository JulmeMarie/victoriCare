import { useEffect, useState } from "react";
import HttpService from "./HttpService";
import { IComment, IResult } from "../utils/global-interfaces";

export default function useComments() {
    const [data, setData] = useState<IResult>({isLoading : true});

    useEffect(() => {
        HttpService.read("/api/Comments/1").then((response) => {
            response.isError = false;
            response.data = comments;
            setData(() => response);
        });
    },[]);

    return data;
}

const comments = [
    {
        username: "VictoriCare",
        role: "Administrateur",
        message: "Bonjour, nous sommes heureux de partager avec vous les commentaires de nos utilisateurs. Merci",
        image: "0.png"
    },
    {
        username: "Samano CASTRE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        image: "1.jpg"
    },
    {
        username: "JULME MARIE WILNIE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        image: "2.jpg"
    },
    {
        username: "JULME MARIE WILNIQUE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        image: "3.jpg"
    },
    {
        username: "DEMOSTHERNE WOODELINE",
        role: "Parent",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        image: "4.jpg"
    },
    {
        username: "DEMOSTHERNE RAYMOND",
        role: "Baby Siter",
        message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
        image: "5.jpg"
    },
] as Array<IComment>;