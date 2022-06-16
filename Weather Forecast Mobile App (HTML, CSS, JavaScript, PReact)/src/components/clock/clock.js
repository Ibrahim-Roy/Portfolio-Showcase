import { h, render, Component } from "preact";

//Custom tools for getting dates, time and future dates
export default class Clock extends Component{
    static days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];;

    constructor(props){
        super(props);
    }

    static SelectDate(dayOffset){
        var today = Clock.GetToday();
        var selectDay = new Date(today);
        selectDay.setDate(selectDay.getDate() + Number(dayOffset));
        return selectDay;
    }

    static GetToday(){
        return new Date();
    }

    static getDate (d) {
        let date = d.getDate();
		let month = d.getMonth() + 1;
		let year = d.getFullYear().toString().substr(-2);

		return `${date}/${month<10?`0${month}`:`${month}`}/${year}`
    }
    
    static getDay(d){
        return Clock.days[d.getDay()];
    }

	static getTime(d) {
		let hour = d.getHours();
		let min = d.getMinutes();

		return `${hour}:${min<10?`0${min}`:`${min}`}`
	}
}