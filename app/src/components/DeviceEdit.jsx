import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from "./AppNavbar";

class DeviceEdit extends Component {
    emptyItem = {
        deviceType: '',
        description: '',
        quantity: 0
    }

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.name !== 'new') {
            const device = await (await fetch('/graphql', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    query:
                        `query deviceByDeviceType {
                        deviceByDeviceType (deviceType: "${this.props.match.params.name}") {
                            id
                            deviceType
                            description
                            quantity
                        }
                    }`
                })
            })).json();
            this.setState({item: device.data.deviceByDeviceType});
        } else {
            this.setState({item: this.emptyItem});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
        console.log(typeof item.quantity);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        let method = item.id ? 'changeDevice' : 'addDevice';
        let addedId = item.id ? 'id: "' + item.id + '", ' : '';
        console.log(method, addedId);
        await fetch('/graphql', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({query:
                    `mutation ${method} {
                        ${method} (${addedId}deviceType: "${item.deviceType}", description: "${item.description}", quantity: ${item.quantity}) {
                            id
                            deviceType
                            description
                            quantity
                        }
                    }`
            })
        }).then(response => response.json()).then(data => console.log(data));
        this.props.history.push('/');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Device' : 'Add Device'}</h2>

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="deviceType">Device Type</Label>
                        <Input type="text" name="deviceType" id="deviceType"
                               value={item.deviceType || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="textarea" name="description" id="description"
                               value={item.description || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="quantity">{item.id ? "Quantity to Add" : "Quantity"}</Label>
                        <Input type="number" name="quantity" id="quantity"
                               value={item.quantity || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(DeviceEdit);