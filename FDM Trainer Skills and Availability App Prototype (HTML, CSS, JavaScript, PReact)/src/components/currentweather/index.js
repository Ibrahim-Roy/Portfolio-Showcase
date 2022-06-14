import { h, render, Component } from 'preact';
import Clock from '../clock/clock';
import style_currentweather from '../currentweather/style_iphone'

//Gets the current numeric temperature
function CurrentTemp(props){
	return (
		<div class = {style_currentweather.currentTempContainer}>
			<h1 class = {style_currentweather.currentTemp}>
				{props.value}
			</h1>
		</div>
	);
}

//Top part of the application showing current weather + date
export default class CurrentWeather extends Component {

	constructor(props){
		super(props);

		this.tick();
	}

	componentDidMount(){
		this.timerID = setInterval(
			() => this.tick(), 1000
		);
	}

	componentWillUnmount() {
		clearInterval(this.timerID);
	}

	componentWillReceiveProps(){
		this.tick();
	}

	//Every second update the date + time
	tick() {
		var today = Clock.SelectDate(this.props.dateOffset);
		this.setState({
			date: Clock.getDate(today),
			dayName: Clock.getDay(today),
			time: Clock.getTime(today),
		});
	}

	renderCurrentTemp(val){
		return (	
			<CurrentTemp value={val}/>
		);
	}

	render() {
		return (
			<div class={style_currentweather.container}>
				{/* Conditions and numeric temp */}
				<div class={style_currentweather.weatherContainer}>
					<div class={style_currentweather.weatherContainerTop}>
						{this.renderCurrentTemp(this.props.temp + "°C")}
						<img class={style_currentweather.weatherIcon} src={"http://openweathermap.org/img/wn/" + this.props.wIcon + "@4x.png"} alt="weather icon"/>
					</div>
					<div class={style_currentweather.weatherContainerBottom}>{this.props.cond}</div>
				</div>
				{/* Date + time */}
				<div class={style_currentweather.currentDetails}>
					<p>{this.state.dayName} {this.state.time}</p>
					<p>{this.state.date}</p>
					<p>{this.props.loc}</p>
				</div>
			</div>
			
		);
	}
}