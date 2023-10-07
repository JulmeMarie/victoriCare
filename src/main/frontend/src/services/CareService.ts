export default class CareService {
    list() {
       return [
        {id:1, startDate:'2012-12-25',endDate:'2012-12-25', user:'Samano', task:{id:1, name:'Faire pipi',procedure:[]}}, 
        {id:1, startDate:'2012-12-25',endDate:'2012-12-26', user:'Pierre', task:{id:2, name:'Faire caca',procedure:[]}}, 
        {id:1, startDate:'2012-12-25',endDate:'2012-12-28', user:'Wilnie', task:{id:3,name:'avoir faim',procedure:[]}}, 
        {id:1, startDate:'2012-12-25',endDate:'2012-12-18', user:'Samano', task:{id:4, name:'s\'endormir',procedure:[]}}, 
        {id:1, startDate:'2012-12-25',endDate:'2012-12-20', user:'Aurelien', task:{id:5, name:'se réveiller', procedure:['vérifier sa couche','si elle a faim, donne lui à manger (sein ou biberon)', 'La laisser jouer dans son berceau ou dans la balancelle', 'ne pas la laisser sans surveillance', 'etc.']}},
        {id:1, startDate:'2012-12-25',endDate:'2012-12-22', user:'Samano', task:{id:6, name:'prendre son bain',procedure:[]}},
        {id:1, startDate:'2012-12-25',endDate:'2012-12-15', user:'Samano', task:{id:7, name:'Pleurer',procedure:[]}}
     ];
    };
    getById(id) {
        let element = this.list().filter((care) => {
            return care.id === parseInt(id);
        });
        return element[0];
    }
}