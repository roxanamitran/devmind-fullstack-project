import { ListGroup } from "react-bootstrap";
function AppointmentDetails({ appointment }) {
  return (
    <ListGroup.Item key={appointment.id}>
      <div>Salon: {appointment.salon.name}</div>
      <div>Pret: {appointment.salonToSalonOffer.price}</div>
      <div>
        Angajat: {appointment.employee.firstName}{" "}
        {appointment.employee.lastName}
      </div>
      <div>Durata: {appointment.salonToSalonOffer.duration} minute</div>
      <div>Ziua: {appointment.startDate}</div>
      <div>Status: {appointment.status}</div>
    </ListGroup.Item>
  );
}

export default AppointmentDetails;
