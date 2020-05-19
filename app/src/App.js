import React, {Component} from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./components/Home";
import DeviceEdit from "./components/DeviceEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/device/:name' component={DeviceEdit}/>
                </Switch>
            </Router>
        );
    }
}

export default App;