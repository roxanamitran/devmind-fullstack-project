import { Carousel } from "react-bootstrap";
import Background from "../assets/slide.jpg";
function CarouselComponent() {
  return (
    <Carousel fade slide={false}>
      <Carousel.Item>
        <img src={Background} alt="First slide" className="d-block w-100" />
        <Carousel.Caption>
          <h3>Cum functioneaza aplicatia noastra?</h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img src={Background} alt="Second slide" className="d-block w-100" />
        <Carousel.Caption>
          <h3>Logheaza-te</h3>
          <p>
            Daca nu ai deja cont la noi, iti poti face unul foate rapid in
            sectiunea "Logare" din bara de meniu.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img src={Background} alt="Third slide" className="d-block w-100" />
        <Carousel.Caption>
          <h3>Alege salonul potrivit dorintelor tale</h3>
          <p>Alege serviciul dorit</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img src={Background} alt="Fourth slide" className="d-block w-100" />
        <Carousel.Caption>
          <h3>Alege data si ora programarii</h3>
          <p>Poti alege ce data si ce ora doresti in limita disponibilitatii</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img src={Background} alt="Fourth slide" className="d-block w-100" />
        <Carousel.Caption>
          <h3>Asteapta confirmarea programarii</h3>
          <p>Vei primi un email de confirmare a programarii.</p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default CarouselComponent;
