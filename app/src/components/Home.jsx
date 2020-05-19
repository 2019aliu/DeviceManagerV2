import React, { Component } from "react";
import { Button, ButtonToolbar, Container, Table } from 'reactstrap';
import AppNavbar from "./AppNavbar";
import { Link } from 'react-router-dom';

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {devices: [], isLoading: true}
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});
        fetch('/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({query:
                    'query {\n' +
                    '    allDevices {\n' +
                    '        id\n' +
                    '        deviceType\n' +
                    '        description\n' +
                    '        quantity\n' +
                    '    }\n' +
                    '}'
            })
        }).then(response => response.json())
            .then(data => this.setState({devices: data.data.allDevices, isLoading: false}));
    }

    async remove(id) {
        await fetch('/graphql', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({query:
                    `mutation deleteDeviceById {
                        deleteDeviceById (id: "${id}") {
                            id
                            deviceType
                            description
                            quantity
                        }
                    }`
            })
        }).then(() => {
            let updatedDevices = [...this.state.devices].filter(i => i.id !== id);
            this.setState({devices: updatedDevices});
        });
    }

    render() {
        const {devices, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const deviceList = devices.map(device => {
            return <tr key={device.id}>
                <td style={{whiteSpace: 'nowrap'}}>{device.deviceType}</td>
                <td>{device.description}</td>
                <td>{device.quantity}</td>
                <td><ButtonToolbar>
                    <Button size="md" color="primary" tag={Link} to={"/device/" + device.deviceType}>Edit</Button>
                    <Button size="md" color="danger" onClick={() => this.remove(device.id)}>Delete</Button>
                </ButtonToolbar></td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/device/new">Add Device</Button>
                    </div>
                    <h3>My S&C Device Manager</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="30%">Device Type</th>
                            <th>Description</th>
                            <th width="10%">Quantity</th>
                            <th width="20%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {deviceList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default Home;