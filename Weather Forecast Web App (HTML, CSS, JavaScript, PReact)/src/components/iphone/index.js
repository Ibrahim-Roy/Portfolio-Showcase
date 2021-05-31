// import preact
import { h, render, Component } from 'preact';
// import stylesheets for ipad & button
import style from './style';

// import jquery for API calls
import $ from 'jquery';
// import the Button component
import CurrentWeather from '../currentweather';
import WeatherFX from '../weatherfx';
import WeatherForecast from '../weatherforecast';
import Settings from '../settings';
import ChangeLocation from '../changelocation';
import ChangeDate from '../changeDate';
import { padStart } from 'core-js/core/string';
import Clock from '../clock/clock';

export default class Iphone extends Component {
	// a constructor with initial set states
	constructor(props){
		super(props);
		//If default location isn't set, auto set it to London
		if (window.localStorage.getItem("defaultLocation") == null){
			window.localStorage.setItem("defaultLocation","London") ;
		}
		this.state.defaultLocation = window.localStorage.getItem("defaultLocation");
		this.state.currentLocation = this.state.defaultLocation;
		this.state.temp = "";

		this.setState({
			changeDate: false,
			changeLocation: false,
			settings: false,

			dateOffset: 0,
		});

		this.fetchWeatherData();
	}

	// a call to fetch weather data via wunderground
	fetchWeatherData = () => {
		// API URL with a structure of : ttp://api.wunderground.com/api/key/feature/q/country-code/city.json
		var url = "http://api.openweathermap.org/data/2.5/weather?q=" + this.state.currentLocation + ",uk&units=metric&APPID=6b39b3e5a7612ca0c2a2d907e942b789";

		$.ajax({
			url: url,
			dataType: "jsonp",
			success : this.parseResponse,
			error : function(req, err){ console.log('API call failed ' + err); }
		})
	}

	//Fetch forecast data
	fetchForecastedData = () => {
		var url = "http://api.openweathermap.org/data/2.5/forecast?q=" + this.state.currentLocation + ",uk&appid=6b39b3e5a7612ca0c2a2d907e942b789";
		$.ajax({
			url: url,
			dataType: "jsonp",
			success : this.parseForecast,
			error : function(req, err){ console.log('API call failed ' + err); }
		})
	}

	//Set the background style dependent on if its night or not
	setBackgroundStyle = () => {
		var today = Clock.GetToday();
		var sunsetTime = new Date(this.state.sunset * 1000);
		var sunriseTime = new Date(this.state.sunrise * 1000);
		//Change to when daytime
		if (today.getTime() < sunsetTime.getTime() && today.getTime() > sunriseTime.getTime()){
			var backgroundStyle = {
				background: "radial-gradient(ellipse at top, #262e7a 40%, transparent 90%),radial-gradient(ellipse at bottom, #059b6e 50%, #126180)",
				zIndex: -5,
				color: "#ffffff",
			};
		}
		else{
			var backgroundStyle = {
				background: "radial-gradient(ellipse at top, #181b33 40%, transparent 90%),radial-gradient(ellipse at bottom, #0d3d2f 50%, #126180)",
				zIndex: -5,
				color: "#fafafa",
			};
		}

		this.setState({
			backgroundStyle: backgroundStyle,
		});
	}

	//Check the background style every second
	componentDidMount(){
		this.timerID = setInterval(
			() => this.setBackgroundStyle(), 1000
		);
	}

	componentWillUnmount() {
		clearInterval(this.timerID);
	}

	handleLocationChanged = (value) => {
		this.setState({
			changeLocation: false,
			currentLocation: value
		});
		this.fetchWeatherData();
	}

	handleDateChanged = (value) => {
		this.setState({
			changeDate: false,
			dateOffset: value
		});

		if (this.state.dateOffset == 0){
			this.fetchWeatherData();
		}else{
			this.fetchForecastedData();
		}
	}

	handleDefaultLocationChanged = (value) => {
		window.localStorage.setItem("defaultLocation", value);
		this.setState({
			settings: false,
			defaultLocation: value,
			currentLocation: value
		});
		this.fetchWeatherData();
	}

	// the main render method for the iphone component
	render() {
		// check if temperature data is fetched, if so add the sign styling to the page
		const tempStyles = this.state.temp ? `${style.temperature} ${style.filled}` : style.temperature;

		//If the icon hasn't loaded yet, show just the background
		if  (typeof(this.state.wIcon) === 'undefined'){
			return (<div style={this.state.backgroundStyle} class={ style.container }></div>);
		}
		// display all weather data
		return(

			<div style={this.state.backgroundStyle} class={ style.container }>
				<div class={style.bringToFront}>
					{/* Weather FX (fog, rain, clear) */}
					<WeatherFX mainCon={this.state.mainCon}/>

					{/* Shows temperature, date, conditions at top */}
					<CurrentWeather temp={Math.round(this.state.temp)} loc={this.state.currentLocation} wIcon={this.state.wIcon} cond ={this.state.cond} dateOffset={this.state.dateOffset}/>

					{/* Forecasted weather */}
					<WeatherForecast loc={ this.state.currentLocation } dateOffset={this.state.dateOffset}/>

					{/* Button menu at the bottom */}
					<div class={ style.menu }>
						<button class={ style.menuButton } onclick={() => {this.setState({changeDate: true})}}>
							<img class={style.menuIcon} src='../assets/icons/calendaricon.png'/>
						</button>
						<button class={ style.menuButton } onclick={() => {this.setState({changeLocation: true})}}>
							<img class={style.menuIcon} src='../assets/icons/locationicon.png'/>
						</button>
						<button class={ style.menuButton } onclick={() => {this.setState({settings: true})}}>
							<img class={style.menuIcon} src='../assets/icons/settingsicon2.png'/>
						</button>
					</div>

					{/* Popups */}
					{this.state.changeDate? <ChangeDate dateOffset={this.state.dateOffset} clickFunction={this.handleDateChanged} cancelFunction={() => this.setState({ changeDate: false })}/> : null}
					{this.state.changeLocation? <ChangeLocation defaultLocation = {this.state.defaultLocation} clickFunction={this.handleLocationChanged} cancelFunction={() => this.setState({ changeLocation: false }) }/> : null}
					{this.state.settings? <Settings defaultLocation = {this.state.defaultLocation} clickFunction={this.handleDefaultLocationChanged} cancelFunction={() => this.setState({ settings: false })}/> : null}
				</div>


			</div>
		);
	}

	// Parse current weather data
	parseResponse = (parsed_json) => {
		const temp_c = parsed_json['main']['temp'];
		const conditions = parsed_json['weather']['0']['description'];
		const mainCon = parsed_json['weather']['0']['main'];
		const wIcon = parsed_json['weather']['0']['icon'];
		const sunrise = parsed_json["sys"]['sunrise'];
		const sunset = parsed_json["sys"]['sunset'];

		// set states for fields so they could be rendered later on
		this.setState({
			temp: temp_c,
			cond : conditions,
			mainCon : mainCon,
			wIcon : wIcon,
			sunrise : sunrise,
			sunset : sunset,
		});

		this.setBackgroundStyle();
	}

	//Parse forecasted weather data
	parseForecast = (parsed_json) => {
		const hourOffset = this.state.dateOffset * 8;

		const forecast_temp = Math.round(parsed_json['list'][hourOffset]['main']['temp'] - 273.15);
		const forecast_cond = parsed_json['list'][hourOffset]['weather']['0']['description'];
		const forecast_mainCon = parsed_json['list'][hourOffset]['weather']['0']['main'];
		const forecast_wIcon = parsed_json['list'][hourOffset]['weather']['0']['icon'];
		const sunrise = parsed_json["city"]['sunrise'];
		const sunset = parsed_json["city"]['sunset'];

		this.setState({
			temp : forecast_temp,
			cond : forecast_cond,
			mainCon : forecast_mainCon,
            wIcon : forecast_wIcon,
            sunrise : sunrise,
			sunset : sunset,
		});
	}
}
