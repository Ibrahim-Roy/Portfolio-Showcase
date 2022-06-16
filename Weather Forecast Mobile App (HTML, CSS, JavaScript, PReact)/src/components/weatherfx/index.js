import { h, render, Component } from 'preact';
import style_weatherfx from '../weatherfx/weatherfx'

//Rain
function rainFX(){
    return(
        <section class={style_weatherfx.rainContain}></section>
    );
}

//Fog
function fogFX(){
    return(
        <div class={style_weatherfx.fogContain}>
            <div class={style_weatherfx.fogImg1}/>
            <div class={style_weatherfx.fogImg2}/>
        </div>
    );
}

export default class WeatherFX extends Component {
    setWeatherFX = () => {
        if(this.props.mainCon == "Rain" || this.props.mainCon == "Drizzle"){
			return rainFX();
        }
        if(this.props.mainCon == "Mist" || this.props.mainCon == "Clouds"){
            return fogFX();
        }
    }

    constructor(props){
        super(props);
        this.setWeatherFX();
    }

	render() {
		return (
			<div class={style_weatherfx.weatherfxContainer}>
                {this.setWeatherFX()}
            </div>
		);
	}
}