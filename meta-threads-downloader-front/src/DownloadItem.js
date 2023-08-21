import React from "react";

const DownloadItem = ({ type, url }) => {
  return (<li>
    <div className="download-items">
      <div className="download-items__thumb">
        {type === 'image' ? (
          <img className="" src={url} alt="threadspthoto" />
        ) : (
          <video autoPlay muted playsInline src={url} alt="threadsvideo" />
        )}
        <span className="format-icon">
          <i className={`icon icon-dl${type === 'image' ? 'image' : 'video'}`}></i>
        </span>
      </div>
      <div className="download-items__btn">
        <a href={url} className="abutton is-success is-fullwidth btn-premium mt-3" rel="nofollow" title={`Download ${type}`}>
          <span><i className="icon icon-download"></i><span>Download {type === 'image' ? 'Image' : 'Video'}</span></span>
        </a>
      </div>
    </div>
  </li>)
}

export default DownloadItem;