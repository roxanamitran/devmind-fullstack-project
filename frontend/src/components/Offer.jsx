import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
function Offer({ offer }) {
  const navigate = useNavigate();

  function goToSalonsByOfferPage() {
    const queryParams = new URLSearchParams({
      salonOffer: offer.name
    }).toString();

    navigate(`/salons?${queryParams}`);
  }

  return (
    <section className="py-5 salonPage">
      <div className="container px-4 px-lg-5 mt-5">
        <div className="row gx-4 gx-lg-5 row-cols-1 row-cols-md-2 row-cols-lg-4">
          <div className="col mb-5">
            <div
              className="card h-100"
              style={{
                minWidth: "300px",
                maxWidth: "300px",
                height: "500px"
              }}
            >
              <img
                src={offer.photoUrl}
                alt="Offer"
                className="card-img-top"
                style={{
                  objectFit: "cover",
                  height: "250px",
                  borderTopLeftRadius: "10px",
                  borderTopRightRadius: "10px"
                }}
              />

              <div
                className="card-body p-4 d-flex flex-column justify-content-between"
                style={{ height: "200px" }}
              >
                <div className="text-center">
                  <h5 className="fw-bolder">{offer.name}</h5>
                </div>
              </div>

              <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div className="text-center">
                  <Button
                    variant="outline-dark"
                    className="mt-auto"
                    onClick={goToSalonsByOfferPage}
                  >
                    Vezi locatii
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Offer;
