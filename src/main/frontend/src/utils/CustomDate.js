export const CustomDate = {
    toString : (dateStr) => {
        if(!dateStr) return null;
        let date = new Date(dateStr);
        return date.toLocaleString('fr-FR');
    }
}