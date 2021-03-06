import React, {useEffect, useState} from "react";
import './App.css';
import axios from "axios";

const fetchData = () => {
  return axios.get('http://localhost:8088/kry/api/services')
  .then((res) => {
      const services = res.data.services;
      services.forEach( (service: Service) => {
        service.status? service.status = 'OK': service.status = 'FAIL';
      })
      return services;
  })
  .catch((err) => {
    console.error(err);
  })
}

const postData = (serviceName: string, url: string) => {
  return axios.post('http://localhost:8088/kry/api/services?serviceName=' + serviceName + "&url=" + encodeURIComponent(url), {})
  .then((res) => {
    return res.data.services[0];
  })
  .catch((err) => {
    console.error(err);
  })
}

export interface Service {
  id: number;
  serviceName: string;
  status: string;
  url: string;
  createdAt: Date;
  lastUpdated: Date;
}

const getServicesKeys = (services: Service[]) => {
  const service = services[0];
  const serviceKeys: string[] = Object.keys(service);
  return serviceKeys;
}

function App() {

  const [services, setServices] = useState<Service[]>([]);
  const [serviceKeys, setServiceKeys] = useState<string[]>([]);
  let servicename : any = React.createRef();
  let serviceurl : any = React.createRef();

  useEffect(() => {
    fetchData().then(services => {
      setServices(services);
      if(services && services.length>0) { setServiceKeys(getServicesKeys(services)); }
    });
  }, [])

  const handleSubmit = (event: any) => {
    event.preventDefault();
    postData(servicename.current.value, serviceurl.current.value)
    .then((newService: Service) => {
      newService.status? newService.status = 'OK': newService.status = 'FAIL';
      const tempServices: Service[] = [...services, newService];
      setServices([...services, newService]);
    })
    .catch((err) => {
      console.error(err);
    });
    event.target.reset();
  }

  return (
    <div className="App">
      <div id="new-service-container">
        <h2> Add a new service </h2>
        <form id="insert-service-form" onSubmit={handleSubmit}>
          <label>
            Service Name: &nbsp;  
            <input type="text" name="servicename" ref={servicename} className="inputElement" />
          </label>
          <label>
            Service url: &nbsp;
            <input type="text" name="url" ref={serviceurl} className="inputElement" />
          </label>
          <input type="submit" value="Submit" />
        </form>
      </div>

      <h2> Registered services </h2>
      <table id="services">
        <thead>
          <tr>
            {serviceKeys && serviceKeys.map((key : string, keyIdx) => (<th key={keyIdx}>
                {key}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {services && services.map((service : Service, serviceIdx) => <tr key={serviceIdx}>
              {Object.values(service).map((value, valueIdx) => <td key={valueIdx}>
                {value}
              </td>
              )}
            </tr>
          )}
        </tbody>
      </table>

    </div>
  );
}

export default App;
