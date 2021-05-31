import { h, render, Component } from 'preact';
import style_currentweather from '../weatherforecast/forecaststyle'

import $ from 'jquery';

//Gets the forecast of a single day
function getForecast(i, forecast_temp, forecast_mainCon, forecast_wIcon, forecast_date, forecast_day){
    return(
        <div class={style_currentweather.forecast}>
            <div><img class={style_currentweather.weatherIcon} src={forecast_wIcon[i]} alt={"forecast " + i}/></div>
            <div class={style_currentweather.weatherSummary}>
                <div class={style_currentweather.weatherTemp}>{forecast_temp[i]}</div>
                <div class={style_currentweather.weatherCond}>{forecast_mainCon[i]}</div>
            </div>
            <div class={style_currentweather.dateSummary}>
                <div class={style_currentweather.day}>{forecast_day[i]}</div>
                <div>{forecast_date[i]}</div>
            </div>
        </div>
    );
}

export default class WeatherForecast extends Component {
    //Gets all 3 day forecast data
    createForecasts = () => {
        const forecast_temp = String(this.state.forecast_temp).split(",").map(i => i);
        const forecast_mainCon = String(this.state.forecast_mainCon).split(",").map(i => i);
        const forecast_wIcon = String(this.state.forecast_wIcon).split(",").map(i => i);
        const forecast_date = String(this.state.forecast_date).split(",").map(i => i);
        const forecast_day = String(this.state.forecast_day).split(",").map(i => i);

        var returnHTML = []
        //Changes amount of data got depending on how much we have available
        for(var i = 0; i < forecast_temp.length; i++){
            returnHTML.push(getForecast(i, forecast_temp, forecast_mainCon, forecast_wIcon, forecast_date, forecast_day));
            if (i < forecast_temp.length-1){
                returnHTML.push(<hr></hr>);
            }
        }
        return returnHTML;
    }

    constructor(props){
        super(props);
        this.state.location = this.props.loc;
        this.state.fetched = false;
        this.fetchWeatherData();
    }

    //Gets forecast data
    fetchWeatherData = () => {
        this.setState({fetched: false});
        var url = "http://api.openweathermap.org/data/2.5/forecast?q=" + this.state.location + ",uk&appid=6b39b3e5a7612ca0c2a2d907e942b789";
		$.ajax({
			url: url,
			dataType: "jsonp",
			success : this.parseForecast,
			error : function(req, err){ console.log('API call failed ' + err); }
		})
    }

    componentWillReceiveProps = (nextProps) => {
        if(this.state.location != nextProps.loc){
            this.setState({
                location: nextProps.loc
            });
            this.fetchWeatherData();
        }
        if(this.state.dateOffset != nextProps.dateOffset){
            this.setState({
                dateOffset: nextProps.dateOffset
            });
            this.fetchWeatherData();
        }
    }

    //Reload forecast data every half an hour
    componentDidMount(){
		this.timerID = setInterval(
			() => this.halfHoulryUpdate(), 1800000
		);
	}

	componentWillUnmount() {
		clearInterval(this.timerID);
	}

	halfHoulryUpdate() {
		this.fetchWeatherData();
	}

    render() {
        return (
            <div class={style_currentweather.forecastContainer}>
                {this.state.fetched ? this.createForecasts() : null}
            </div>
        );
    }

    //Save data from API into state
    parseForecast = (parsed_json) => {
		var forecast_temp = [];
		var forecast_mainCon = [];
        var forecast_wIcon = [];
        var forecast_date = [];
        var forecast_day = [];

        var hourOffset = this.props.dateOffset * 8;
        var maxOffset = Math.min(hourOffset + 24, 32);

		for(var i = 8 + hourOffset; i <= maxOffset; i=i+8){

			//Convert from Kelvin to Celsius
            forecast_temp.push(Math.round(parsed_json['list'][i]['main']['temp'] - 273.15));
            forecast_mainCon.push(parsed_json['list'][i]['weather']['0']['main']);
			forecast_wIcon.push("http://openweathermap.org/img/wn/" +
                parsed_json['list'][i]['weather']['0']['icon']
                + "@2x.png");
            const parsedDate = new Date(Date.parse(parsed_json['list'][i]['dt_txt']));
            let date = parsedDate.getDate();
		    let month = parsedDate.getMonth() + 1;
		    let year = parsedDate.getFullYear().toString().substr(-2);
            forecast_date.push(`${date}/${month<10?`0${month}`:`${month}`}/${year}`);
            const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
            forecast_day.push(days[parsedDate.getDay()])
        }

		this.setState({
			forecast_temp : forecast_temp,
			forecast_mainCon : forecast_mainCon,
            forecast_wIcon : forecast_wIcon,
            forecast_date : forecast_date,
            forecast_day : forecast_day,
            fetched: true
		});
	}
}
