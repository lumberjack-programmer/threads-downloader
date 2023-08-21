import { useState } from 'react';
import './App.css';
import DownloadItem from './DownloadItem';

const App = () => {
  const [url, setUrl] = useState('');
  const [downloadItems, setDownloadItems] = useState([]);

  const handleDownload = () => {
    const baseUrl = 'http://localhost:8080/api/v1/post/getAll?url=';
    const inputUrl = url.trim();
    const fullUrl = baseUrl + inputUrl;
    console.log(fullUrl);
    fetch(fullUrl)
      .then(res => {
        if (!res.ok) {
          throw new Error('Network response error.')
        }
        return res.json();
      })
      .then(data => {
        const updatedDownloadItems = [];

        if (data && data.imageUrls && data.videoUrls) {
          data.imageUrls.forEach(imageUrl => {
            updatedDownloadItems.push({ type: 'image', url: imageUrl });
          });

          data.videoUrls.forEach(videoUrl => {
            updatedDownloadItems.push({ type: 'video', url: videoUrl });
          });

          setDownloadItems(updatedDownloadItems);
        } else {
          console.error('Invalid data format:', data);
        }

      })
      .catch(error => {
        console.log('Error fetching data: ', error);
      })
  }

  const handleInputChange = (event) => {
    setUrl(event.target.value);
  };

  return (
    <div className="container-app" styleName="height: auto !important;">
      <div className="bg-color">

        <div className="container">
          <div className="threads-section threads-gr-content center" id="convert">
            <div className="threads-page-convert">
              <h1 className="title">Threads Video &amp; Photo Downloader</h1>
            </div>
            <div id="search-form" className="search-form relative">
              <div className="input-group">
                <input
                  type="text"
                  name="url"
                  id="url"
                  className="form-control"
                  placeholder="Paste URL Threads"
                  value={url}
                  onChange={handleInputChange} />
                <div className="input-group-btn">
                  <span className="paste" id="paste"><span><i className="icon-btn icon-paste"></i>Paste</span></span>
                  <button id="download" type="button" className="btn btn-default" onClick={handleDownload}> <b>Download</b> </button>
                </div>
              </div>
            </div>
          </div >
        </div>
      </div>


      <div className="container" style={{ height: 'auto !important' }}>
        <div className="threads-section" style={{ height: 'auto !important' }}>
          <div className="download-section">
            <div id="search-result" className="">
              <ul className="download-box">
                {console.log(downloadItems.length)}
                {downloadItems.map((item, index) => (
                  <DownloadItem key={index} type={item.type} url={item.url} />
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

}

export default App;
