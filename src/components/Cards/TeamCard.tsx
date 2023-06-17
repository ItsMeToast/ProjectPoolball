import "./TeamCard.css";

function TeamCard(props: any) {
  return (
    <div className="teamcard container flex-column text-center align-items-center">
      <div className="teamcard-code">{props.code}</div>
      <img className="teamcard-logo no-interact" src={"../public/" + props.code + ".png"} />
    </div>
  );
}

export default TeamCard;
