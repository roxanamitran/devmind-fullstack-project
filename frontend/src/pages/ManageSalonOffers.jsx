import { useState, useEffect } from "react";
import { Form, Button, Table } from "react-bootstrap";
import apiClient from "../api/axios";
import { useNavigate } from "react-router-dom";

function ManageSalonOffers() {
  const [salonOffers, setSalonOffers] = useState([]);
  const [salonToSalonOffers, setSalonToSalonOffers] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const navigate = useNavigate();

  const [salonOfferId, setSalonOfferId] = useState("");
  const [duration, setDuration] = useState("");
  const [price, setPrice] = useState(0);

  const queryParams = new URLSearchParams(location.search);
  const salonId = queryParams.get("salon_id");

  const handleDelete = async (id) => {
    await apiClient.delete(`salon-to-salon-offers/${id}`);
    setRefresh((refresh) => !refresh);
  };

  function backToSalon() {
    navigate(`/salons/${salonId}`);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    const salonToSalonOffer = {
      price,
      duration,
      salon: {
        id: salonId
      },
      salonOffer: {
        id: salonOfferId
      }
    };

    try {
      await apiClient.post(`salon-to-salon-offers`, salonToSalonOffer);

      setRefresh((refresh) => !refresh);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (salonId) {
      const fetchData = async () => {
        try {
          const salonOffers = await apiClient.get("/salonOffers");
          const salonToSalonOffers = await apiClient.get(
            `/salon-to-salon-offers/salonOffers/${salonId}`
          );

          if (salonOffers.data && salonOffers.data.length > 0) {
            setSalonOfferId(salonOffers.data[0].id);
            setSalonOffers(salonOffers.data);
          }

          if (salonToSalonOffers.data && salonOffers.data.length > 0) {
            setSalonToSalonOffers(salonToSalonOffers.data);
          }
        } catch (error) {
          console.error("Error fetching data:", error);
        }
      };
      fetchData();
    }
  }, [refresh]);

  if (!salonId) {
    return "No salon selected";
  }

  return (
    <div>
      <div className="centrat">
        <h1>Gestioneaza serviciile salonului tau!</h1>
      </div>
      <div className="form3">
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>
              Alege serviciul
              <Form.Select
                type="text"
                value={salonOfferId}
                onChange={(e) => setSalonOfferId(e.target.value)}
              >
                {salonOffers.map((salonOffer) => (
                  <option key={salonOffer.id} value={salonOffer.id}>
                    {salonOffer.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Label>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Durata serviciului(minute)</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti durata serviciului"
              value={duration}
              onChange={(e) => setDuration(e.target.value)}
            ></Form.Control>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Pretul(lei)</Form.Label>
            <Form.Control
              type="number"
              placeholder="Introduceti pretul serviciului"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </Form.Group>

          <Button variant="success" type="submit" size="lg">
            Salveaza modificarile facute
          </Button>
        </Form>

        <Table>
          <thead>
            <tr>
              <th>Serviciu</th>
              <th>Durata</th>
              <th>Pret</th>
            </tr>
          </thead>
          <tbody>
            {salonToSalonOffers.map((salonToSalonOffer) => {
              return (
                <tr key={salonToSalonOffer.id}>
                  <td>{salonToSalonOffer.salonOffer.name}</td>
                  <td>{salonToSalonOffer.duration}</td>
                  <td>{salonToSalonOffer.price}</td>
                  <td>
                    <Button
                      variant="danger"
                      onClick={() => handleDelete(salonToSalonOffer.id)}
                    >
                      Sterge
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
        <Button variant="dark" onClick={backToSalon}>
          Inapoi la salon
        </Button>
      </div>
    </div>
  );
}

export default ManageSalonOffers;
