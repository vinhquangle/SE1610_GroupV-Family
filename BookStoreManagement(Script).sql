﻿CREATE DATABASE BookStoreManagement
GO

USE BookStoreManagement
GO

CREATE TABLE [tblCustomer]
(
	customerID VARCHAR(50) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	[Password] VARCHAR(30) NOT NULL,
	Email VARCHAR(50) NOT NULL,
	[Address] NVARCHAR(200),
	Phone VARCHAR(20) NOT NULL,
	Point INT NOT NULL,
	[Status] BIT,
	[Delete] BIT NOT NULL,
	PRIMARY KEY (customerID),
);

CREATE TABLE [tblStaff]
(
	staffID VARCHAR(10) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	[Password] VARCHAR(30) NOT NULL,
	[Role] NVARCHAR(30) NOT NULL,
	Phone VARCHAR(20),
	[Date-of-birth] DATE,
	[Status] BIT,
	[Delete] BIT NOT NULL,
	PRIMARY KEY (staffID),
);

CREATE TABLE [tblOrder]
(
	orderID INT IDENTITY(1,1) NOT NULL,
	customerID VARCHAR(50),
	staffID VARCHAR(10),
	[Date] DATE NOT NULL,
	[Delivery-cost] DECIMAL(10,2),
	Total DECIMAL(10,2) NOT NULL,
	[Status] BIT,
	PRIMARY KEY (orderID),
	FOREIGN KEY (customerID) REFERENCES [tblCustomer](customerID),
	FOREIGN KEY (staffID) REFERENCES [tblStaff](staffID),
);

CREATE TABLE [tblCategory]
(
  categoryID VARCHAR(10) NOT NULL,
  Name NVARCHAR(50) NOT NULL,
  PRIMARY KEY (categoryID),
);

CREATE TABLE [tblPublisher]
(
	publisherID VARCHAR(10) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	PRIMARY KEY (publisherID),
);

CREATE TABLE [tblBook]
(
	ISBN VARCHAR(17) NOT NULL,
	Name NVARCHAR(150) NOT NULL,
	publisherID VARCHAR(10) NOT NULL,
	categoryID VARCHAR(10) NOT NULL,	
	[Author-name] NVARCHAR(50),
	Price DECIMAL(10,2) NOT NULL,
	[Image] VARCHAR(500) NOT NULL,
	Quantity INT NOT NULL,
	PRIMARY KEY (ISBN),
	FOREIGN KEY (publisherID) REFERENCES [tblPublisher](publisherID),
	FOREIGN KEY (categoryID) REFERENCES [tblCategory](categoryID),
);

CREATE TABLE [tblOrderDetail]
(
	detailID INT IDENTITY(1,1) NOT NULL,
	orderID INT NOT NULL,
	ISBN VARCHAR(17) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	publisherID VARCHAR(10) NOT NULL,
	categoryID VARCHAR(10) NOT NULL,	
	[Author-name] NVARCHAR(50),
	Price DECIMAL(10,2),
	Quantity INT NOT NULL,
	Total DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (detailID),
	FOREIGN KEY (orderID) REFERENCES [tblOrder](orderID),
	FOREIGN KEY (ISBN) REFERENCES [tblBook](ISBN),
);

CREATE TABLE [tblBookRequest]
(
	requestID INT IDENTITY(1,1) NOT NULL,
	staffID VARCHAR(10) NOT NULL,
	[Date] DATE NOT NULL,
	[Status] BIT NOT NULL,
	PRIMARY KEY (requestID),
	FOREIGN KEY (staffID) REFERENCES [tblStaff](staffID),
);

CREATE TABLE [tblBRequestDetail]
(
	requestDetailID INT IDENTITY(1,1) NOT NULL,
	requestID INT NOT NULL,
	ISBN VARCHAR(17) NOT NULL,
	publisherID VARCHAR(10) NOT NULL,
	categoryID VARCHAR(10) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	[Author-name] NVARCHAR(50),
	Quantity INT NOT NULL,
	PRIMARY KEY (requestDetailID),
	FOREIGN KEY (requestID) REFERENCES [tblBookRequest](requestID),
	FOREIGN KEY (ISBN) REFERENCES [tblBook](ISBN),
);

CREATE TABLE [tblBookResponse]
(
	responseID INT IDENTITY(1,1) NOT NULL,
	requestID INT NOT NULL,
	staffID VARCHAR(10) NOT NULL,
	[Date] DATE NOT NULL,
	[Status] BIT NOT NULL,
	PRIMARY KEY (responseID),
	FOREIGN KEY (requestID) REFERENCES [tblBookRequest](requestID),
);

CREATE TABLE [tblBResponseDetail]
(
	responseDetailID INT IDENTITY(1,1) NOT NULL,
	responseID INT NOT NULL,
	ISBN VARCHAR(17) NOT NULL,
	publisherID VARCHAR(10) NOT NULL,
	categoryID VARCHAR(10) NOT NULL,
	Name NVARCHAR(50) NOT NULL,
	[Author-name] NVARCHAR(50),
	Quantity INT NOT NULL,
	Price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (responseDetailID),
	FOREIGN KEY (responseID) REFERENCES [tblBookResponse](responseID),
	FOREIGN KEY (ISBN) REFERENCES [tblBook](ISBN),
);

--Insert data for tblCustomer
INSERT INTO [tblCustomer](customerID,Name,[Password],Email,[Address],Phone,Point,[Status],[Delete]) 
VALUES ('cus1',N'Phạm Quốc Thịnh','1','thinhphamquoc9999@gmail.com',N'Hiệp Thành, Quận 12,TP HCM','0938081927',0,0,0)

--Insert data for tblStaff
INSERT INTO [tblStaff](staffID,Name,[Password],[Role],Phone,[Date-of-birth],[Status],[Delete]) 
VALUES ('ad1',N'Tui là Admin','1',N'Admin','093696969','2002-10-12',0,0)

INSERT INTO [tblStaff](staffID,Name,[Password],[Role],Phone,[Date-of-birth],[Status],[Delete]) 
VALUES ('st1',N'Tui là nhân viên','1',N'Staff','093696969','2003-10-12',0,0)

INSERT INTO [tblStaff](staffID,Name,[Password],[Role],Phone,[Date-of-birth],[Status],[Delete])
VALUES ('del1',N'Tui là deliverer','1',N'Deliverer','0123456789','2003-05-03',0,0)

--Insert data for tblOrder
INSERT INTO [tblOrder](customerID,staffID,[Date],[Delivery-cost],Total,[Status])
VALUES ('cus1','del1','2022-05-03',20000,255000,0)

--Insert data for tblCategory
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C1',N'Văn học')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C2',N'Tác phẩm kinh điển')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C3',N'Tiểu thuyết')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C4',N'Tâm lý')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C5',N'Kinh tế')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C6',N'Lịch sử')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C7',N'Tự truyện hồi kí')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C8',N'Kỹ năng sống')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C9',N'Tôn giáo')
INSERT INTO [tblCategory](categoryID,Name) VALUES ('C10',N'Tiểu thuyết trinh thám')


--Insert data for tblPublisher
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P1',N'NXB Văn Học')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P2',N'NXB Hội Nhà Văn')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P3',N'NXB Trẻ')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P4',N'NXB Thế Giới')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P5',N'NXB Dân Trí')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P6',N'NXB Phụ Nữ Việt Nam')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P7',N'NXB Công Thương')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P8',N'NXB Lao Động')
INSERT INTO [tblPublisher](publisherID,Name) VALUES ('P9',N'NXB Kim Đồng')

--Insert data for tblBook
--1
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049549090',N'Lục Vân Tiên','P1','C1',N'Nguyễn Đình Chiểu',235000,'https://salt.tikicdn.com/cache/w1200/media/catalog/product/5/_/5.u5552.d20170929.t183543.444888.jpg',100)
--2
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043282085',N'Truyện Kiều (Tái Bản)','P1','C1',N'Nguyễn Du',54000,'https://product.hstatic.net/1000237375/product/bia_900x900_dbb77079df0641a5a3c1e4a8064fa6ab_master.jpg',100)
--3
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049696398',N'Hai số phận','P1','C2',N'Jeffrey Archer',126000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_179484.jpg',100)
--4
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049868597',N'Đồi Gió Hú','P1','C2',N'Emily Dronte',93000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_48045.jpg',100)
--5
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043068122',N'Cây Cam Ngọt Của Tôi','P2','C3',N'José Mauro de Vasconcelos',81000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_217480.jpg',100)
--6
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786041116313',N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh','P3','C3',N'Nguyễn Nhật Ánh',96000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_180164_1_43_1_57_1_4_1_2_1_210_1_29_1_98_1_25_1_21_1_5_1_3_1_18_1_18_1_45_1_26_1_32_1_14_1_2199.jpg',100)
--7
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786047763092',N'Thiên Tài Bên Trái, Kẻ Điên Bên Phải','P4','C4',N'Cao Minh',125000,'https://cdn0.fahasa.com/media/catalog/product/b/_/b_a-thi_n-t_i-b_n-tr_i-k_-_i_n-b_n-ph_i_1.jpg',100)
--8
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043140156',N'Tâm Lý Học Về Tiền','P5','C4',N'Morgan Housel',132000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_220008.jpg',100)
--9
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786041108264',N'Gã Nghiện Giày - Tự Truyện Của Nhà Sáng Lập Nike','P3','C5',N'Phil Knight',160000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8934974150961.jpg',100)
--10
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786047740796',N'Homo Deus - Lược Sử Tương Lai','P4','C6',N'Yuval Noah Harari',179000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_176929.jpg',100)
--11
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8936071674913',N'Danh Tướng','P5','C6',N'Yuval Noah Harari',475000,'https://cdn0.fahasa.com/media/catalog/product/b/i/bia-danh-tuong-web.jpg',100)
--12
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8934974180944',N'Sinh Vào Ngày Xanh (Tái bản năm 2022)','P3','C7',N'Daniel Tammet',140000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/235/sinh-vao-ngay-xanh-tb-2022.jpg',100)
--13
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786041084278',N'Harry Potter Và Chiếc Cốc Lửa - Tập 4 (Tái bản năm 2022)','P1','C1',N'J. K. Rowling',310000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/235/harry-potter-va-chiec-coc-lua-tap-4-tb-2022.jpg',100)
--14
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786046990789',N'Những Thử Thách Của Apollo (Phần 1) - Sấm Truyền Bí Ẩn','P4','C6',N'Rick Riordan',113000,'https://nhasachphuongnam.com/images/thumbnails/600/600/detailed/234/nhung-thu-thach-cua-apollo-1-sam-truyen-bi-an-tb-2022.jpg',100)
--15
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8935210302243',N'Những Nhà Sáng Lập Siêu Đẳng - Dữ Liệu Tiết Lộ Điều Gì Về Các Công Ty Khởi Nghiệp Tỉ Đô','P5','C5',N'Ali Tamaseb',165000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/nhung-nha-sang-lap-sieu-dang.jpg',100)
--16
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043654387',N'Làm Tốt Hơn Với Nguồn Lực Ít Hơn','P4','C5',N'Jaideep Prabhu',220000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/lam-tot-hon-voi-nguon-luc-it-hon.jpg',100)
--17
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8935235234727',N'Người Hướng Nội Trong Thế Giới Hướng Ngoại','P5','C8',N'Insook Nam',105000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/nguoi-huong-noi-trong-the-gioi-huong-ngoai.jpg',100)
--18
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043458497',N'Giao Tiếp Khiêm Nhường - Thu Phục Nhân Tâm','P4','C8',N'Edgar H. Schein',135000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/giao-tiep-khiem-nhuong-thu-phuc-nhan-tam.jpg',100)
--19
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043654844',N'Đến Apple Học Về Sáng Tạo','P4','C5',N'Ali Tamaseb',180000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/den-apple-hoc-ve-sang-tao.jpg',100)
--20
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8935235235083',N'1 Năm Bằng 10 Năm - Bí Quyết Nâng Cấp Của Cải Và Sức Ảnh Hưởng Của Mỗi Người','P5','C8',N'Miêu Thúc',125000,'https://nhasachphuongnam.com/images/thumbnails/900/900/detailed/234/1-nam-bang-10-nam.jpg',100)
--21
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786046967378',N'Những Cuộc Phiêu Lưu Của Huckleberry Finn','P1','C1',N'Mark Twain',83000,'https://salt.tikicdn.com/cache/w1200/media/catalog/product/n/h/nhung%20cuoc%20phieu%20luu%20cua%20huckleberry%20finn%20-%20copy.u547.d20160404.t160641.jpg',100)
--22
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049959110',N'Chiến Binh Cầu Vồng','P2','C1',N'Andrea Hirata',109000,'https://salt.tikicdn.com/cache/750x750/ts/product/a1/ef/4f/0b39e40dca3827604c8bc4e867cc9423.jpg.webp',100)
--23
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786045364888',N'Totto-chan Bên Cửa Sổ','P2','C1',N'Kuroyanagi Tetsuko',98000,'https://salt.tikicdn.com/cache/750x750/ts/product/24/39/01/1718d16b33315c03026cee717adad4b3.jpg.webp',100)
--24
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786047747313',N'Con Đường Thoát Hạn','P4','C8',N'Seth M Siegel',169000,'https://salt.tikicdn.com/cache/w1200/media/catalog/product/c/o/con%20duong%20thoat%20han%20ban%20tieng%20viet.u2469.d20160823.t105118.805071.jpg',100)
--25
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043206258',N'Sao Chúng Ta Lại Ngủ','P8','C8',N'Matthew Walker',249000,'https://salt.tikicdn.com/cache/750x750/ts/product/5b/9b/72/693e8880ba84cf2c4a85dfc8081b4a5b.jpg.webp',100)
--26
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786047777167',N'Tư Duy Nhanh Và Chậm','P4','C5',N'Daniel Kahneman',239000,'https://salt.tikicdn.com/cache/750x750/ts/product/77/3c/9e/6deec49282e3416f38b46e57d1ffd79f.jpg.webp',100)
--27
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049316043',N'Hacker Lược Sử','P7','C8',N'Steven Levy',299000,'https://salt.tikicdn.com/cache/750x750/ts/product/a9/23/d7/7d8612e8ba7e475a5cbab2c6cae7dcc6.jpg.webp',100)
--28
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049768361',N'Những Cuộc Phiêu Lưu Của Tom Sawyer','P1','C1',N'Mark Twain',134000,'https://salt.tikicdn.com/cache/750x750/ts/product/9b/49/8d/732cfcfec6164bd960031e560927b4ba.jpg.webp',100)
--29
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786047789467',N'Hành Trình Về Phương Đông','P4','C9',N'Baird T Spalding',118000,'https://salt.tikicdn.com/cache/750x750/ts/product/ae/39/8a/3da6ccb3b24dbae4a0f4ed1b75778467.jpg.webp',100)
--30
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786045990049',N'Blockchain Bản Chất Của Blockchain, Bitcoin, Tiền Điện Tử, Hợp Đồng Thông Minh Và Tương Lai Của Tiền Tệ','P8','C5',N'Mark Gates',110000,'https://salt.tikicdn.com/cache/750x750/ts/product/da/38/1f/9b221772de663643f808efe8ef1d25eb.jpg.webp',100)
--31
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049973949',N'Không Gia Đình','P1','C3',N'Hector Malot',185000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_47394.jpg',100)
--32
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043236941',N'Những Người Phụ Nữ Bé Nhỏ','P1','C3',N'Louisa May Alcott',388000,'https://cdn0.fahasa.com/media/catalog/product/n/h/nh_ng-ng_i-ph_-n_-b_-nh_kkkkkkk-bc_1.jpg',100)
--33
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786046937098',N'Bố Già (Bìa mềm)','P5','C3',N'Mario Puzo',110000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8936071673381.jpg',100)
--34
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043234732',N'Bố Già (Bìa cứng)','P1','C3',N'Mario Puzo',250000,'https://cdn0.fahasa.com/media/catalog/product/z/2/z2611575615164_9f60c133cfed1c7bb3f59b247f-600.jpg',100)
--35
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043077957',N'Những Người Khốn Khổ (Trọn Bộ 2 Tập)','P1','C2',N'Victor Hugo',499000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_237627.jpg',100)
--36
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049937309',N'Chúa Ruồi','P1','C3',N'William Golding',88000,'https://cdn0.fahasa.com/media/catalog/product/c/h/chuaruoi.jpg',100)
--37
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043231496',N'Sherlock Holmes Toàn Tập - Tập 1 (Bìa Cứng)','P1','C10',N'Arthur Conan Doyle',100000,'https://salt.tikicdn.com/cache/w1200/ts/product/fc/72/6b/c3fa449783cd2b97dfdc3c46bbc6fbbd.jpg',100)
--38
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043235173',N'Sherlock Holmes Toàn Tập - Tập 2 (Bìa Cứng)','P1','C10',N'Arthur Conan Doyle',145000,'https://salt.tikicdn.com/cache/w1200/ts/product/72/51/40/88c645222cbb18bbbb9578eabe112b98.jpg',100)
--39
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043235180',N'Sherlock Holmes Toàn Tập - Tập 3 (Bìa Cứng)','P1','C10',N'Arthur Conan Doyle',130000,'https://salt.tikicdn.com/cache/750x750/ts/product/98/fb/6b/8afaf7b08aa4c6e408181baf4c6fdeaa.jpg.webp',100)
--40
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043075892',N'Tiếng Gọi Của Hoang Dã','P1','C3',N'auname',50000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_10705.jpg',100)
--41
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043076394',N'Thể Xác Và Tâm Hồn (Bìa Cứng)','P1','C1',N'Maxence Van der Meersch',368000,'https://cdn0.fahasa.com/media/catalog/product/x/v/xvth.jpg',100)
--42
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786046982944',N'Thép Đã Tôi Thế Đấy','P1','C1',N'auname',110000,'https://salt.tikicdn.com/cache/w1200/ts/product/57/9d/f1/f3395afd678d2e6a90e314479b865c00.jpg',100)
--43
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786046989233',N'Nhật Ký Của Một Người Thừa','P1','C2',N'Ivan Turcenev',82000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_7073.jpg',100)
--44
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786049699580',N'Những Câu Chuyện Về Khu Phố Nhỏ Ven Sông','P1','C2',N'Jan Neruda',115000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_180164_1_43_1_57_1_4_1_2_1_210_1_29_1_98_1_25_1_21_1_5_1_3_1_18_1_18_1_45_1_26_1_32_1_14_1_2351.jpg',100)
--45
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8936071677167',N'Khát Vọng Sống','P1','C1',N'Irving Stone',195000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_26263.jpg',100)
--46
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786043235272',N'Những Tấm Lòng Cao Cả','P1','C3',N'Edmondo De Amicis',86000,'https://cdn0.fahasa.com/media/catalog/product/n/h/nhung_tam_long_cao_ca_tai_ban_2018_1_2019_01_03_02_09_21.jpg',100)
--47
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786042274036',N'Truyện Ngắn Nguyễn Minh Châu','P9','C1',N'Nguyễn Minh Châu',78000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8935244877137.jpg',100)
--48
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786042270823',N'Lớn Rồi Hết Sợ','P9','C1',N'Hồ Anh Thái',110000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8935244876321.jpg',100)
--49
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786042272049',N'Đoàn Binh Tây Tiến','P9','C1',N'Quang Dũng',45000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8935244872545.jpg',100)
--50
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('9786042272704',N'Ngọn Cờ Lau','P9','C1',N'Tô Hoài và Nguyễn Hồng Anh',90000,'https://cdn0.fahasa.com/media/catalog/product/8/9/8935244873771.jpg',100)
--51
INSERT INTO [tblBook](ISBN,Name,publisherID,categoryID,[Author-name],Price,[Image],Quantity)
VALUES ('8935212352895',N'Thượng Dương','P6','C3',N'Hoàng Yến',108000,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_216250.jpg',100)

--Insert data for tblOrderDetail
INSERT INTO [tblOrderDetail](orderID,ISBN,Name,publisherID,categoryID,Price,Quantity,Total)
VALUES ('1','9786049549090',N'Lục Vân Tiên','P1','C1',235000,1,235000)

--Insert data for tblBookRequest
INSERT INTO [tblBookRequest](staffID,[Date],[Status])
VALUES ('st1','2022-09-14',1)

--Insert data for tblBRequestDetail
INSERT INTO tblBRequestDetail(ISBN,requestID,publisherID,categoryID,Name,[Author-name],Quantity)
VALUES ('9786049549090','1','P1','C1',N'Lục Vân Tiên',N'Nguyễn Đình Chiểu',10)

--Insert data for tblBookResponse
INSERT INTO tblBookResponse(requestID,staffID,[Date],[Status])
VALUES ('1','st1','2022-09-14',1)

--Insert data for tblBResponseDetail
INSERT INTO tblBResponseDetail(ISBN,responseID,publisherID,categoryID,Name,[Author-name],Quantity,Price)
VALUES ('9786049549090','1','P1','C1',N'Lục Vân Tiên',N'Nguyễn Đình Chiểu',10,200000)

CREATE FUNCTION [dbo].[ufn_removeMark] (@text nvarchar(max))
RETURNS nvarchar(max)
AS
BEGIN
    SET @text = LOWER(@text)
    DECLARE @textLen int = LEN(@text)
    IF @textLen > 0
    BEGIN
        DECLARE @index int = 1
        DECLARE @lPos int
        DECLARE @SIGN_CHARS nvarchar(100) = N'ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýđð'
        DECLARE @UNSIGN_CHARS varchar(100) = 'aadeoouaaaaaaaaaaaaaaaeeeeeeeeeeiiiiiooooooooooooooouuuuuuuuuuyyyyydd'
 
        WHILE @index <= @textLen
        BEGIN
            SET @lPos = CHARINDEX(SUBSTRING(@text,@index,1),@SIGN_CHARS)
            IF @lPos > 0
            BEGIN
                SET @text = STUFF(@text,@index,1,SUBSTRING(@UNSIGN_CHARS,@lPos,1))
            END
            SET @index = @index + 1
        END
    END
    RETURN @text
END